package org.bbop.apollo

import grails.transaction.Transactional
import grails.compiler.GrailsCompileStatic
import org.bbop.apollo.editor.AnnotationEditor
import org.bbop.apollo.event.AnnotationEvent
import org.bbop.apollo.sequence.SequenceTranslationHandler

//@GrailsCompileStatic
@Transactional
class ExonService {

//    CvTermService cvTermService
    def transcriptService
    def featureService
    def featureRelationshipService
    def sequenceService
    def nameService

    /** Retrieve the transcript that this exon is associated with.  Uses the configuration to
     * determine which parent is a transcript.  The transcript object is generated on the fly.  Returns
     * <code>null</code> if this exon is not associated with any transcript.
     *
     * @return Transcript that this Exon is associated with
     */
    public Transcript getTranscript(Exon exon) {

        // this could be for any transcript, though
        return (Transcript) featureRelationshipService.getParentForFeature(exon,transcriptService.ontologyIds as String[])
    }

    /**
     * Merge exon1 and exon2.  The "newly" created exon retains exon1's ID.
     *
     * @param exon1 - Exon to be merged to
     * @param exon2 - Exon to be merged with
     * @throws AnnotationException - If exons don't belong to the same transcript or are in separate strands
     */
    public void mergeExons(Exon exon1, Exon exon2) throws AnnotationException {
//        // both exons must be part of the same transcript
//        if (!getTranscript(exon1).equals(getTranscript(exon2))) {
//            throw new AnnotationEditorException("mergeExons(): Exons must have same parent transcript", exon1, exon2);
//        }
        // both exons must be in the same strand
        Transcript transcript = getTranscript(exon1);
        if (!exon1?.featureLocation?.getStrand()?.equals(exon2?.featureLocation?.getStrand())) {
            throw new AnnotationException("mergeExons(): Exons must be in the same strand ${exon1} ${exon2}");
        }
        if (exon1.getFmin() > exon2.getFmin()) {
            setFmin(exon1, exon2.getFmin())
//            exon1.setFmin(exon2.getFmin());
        }
        if (exon1.getFmax() < exon2.getFmax()) {
            setFmax(exon1, exon2.fmax)
//            exon1.setFmax(exon2.getFmax());
        }
        // need to delete exon2 from transcript
        if (getTranscript(exon2) != null) {
            deleteExon(getTranscript(exon2), exon2);
        }
        
//        setLongestORF(getTranscript(exon1));
        featureService.removeExonOverlapsAndAdjacencies(transcript);

//        Date date = new Date();
//        exon1.setTimeLastModified(date);
//        transcript.setTimeLastModified(date);

        // TODO: event fire
//        fireAnnotationChangeEvent(transcript, transcript.getGene(), AnnotationChangeEvent.Operation.UPDATE);

    }


    /**
     * Delete an exon from a transcript.  If there are no exons left on the transcript, the transcript
     * is deleted from the parent gene.
     *
     * @param transcript - Transcript to have the exon deleted from
     * @param exon - Exon to be deleted from the transcript
     */
    public void deleteExon(Transcript transcript, Exon exon) {
        featureRelationshipService.removeFeatureRelationship(transcript,exon)


        // an empty transcript should be removed from gene,  TODO??
//        if (transcript.getNumberOfExons() == 0) {
//            if (transcript.getGene() != null) {
//                deleteTranscript(transcript.getGene(), transcript);
//            }
//            else {
//                deleteFeature(transcript);
//            }
//        }
//        else {
//            setLongestORF(transcript);
//        }
        // update transcript boundaries if necessary
        if (exon.getFmin().equals(transcript.getFmin())) {
            int fmin = Integer.MAX_VALUE;
            for (Exon e : transcriptService.getExons(transcript)) {
                if (e.getFmin() < fmin) {
                    fmin = e.getFmin();
                }
            }
            transcriptService.setFmin(transcript,fmin);
        }
        if (exon.getFmax().equals(transcript.getFmax())) {
            int fmax = Integer.MIN_VALUE;
            for (Exon e : transcriptService.getExons(transcript)) {
                if (e.getFmax() > fmax) {
                    fmax = e.getFmax();
                }
            }
            transcriptService.setFmax(transcript,fmax);
        }
        // update gene boundaries if necessary
        transcriptService.updateGeneBoundaries(transcript);

//        FeatureLocation.deleteAll(exon.featureLocations)
        exon.save(flush: true)
        exon.featureLocations.clear()
        exon.parentFeatureRelationships?.clear()
        exon.childFeatureRelationships?.clear()
        List<FeatureRelationship> parentFeatures = FeatureRelationship.findAllByChildFeature(exon)
        def childFeatures = FeatureRelationship.findAllByParentFeature(exon)
        if(parentFeatures){
            parentFeatures.each { FeatureRelationship it ->
                FeatureRelationship.executeUpdate("delete from FeatureRelationship fr where fr.id = :frid",[frid:it.id])
            }
        }

        Exon.executeUpdate("delete from Exon e where e.id = :exonId",[exonId:exon.id])
//        Exon.deleteAll(exon)
        transcript.save(flush: true)


    }


    public void setFmin(Exon exon, Integer fmin) {
        exon.getFeatureLocation().setFmin(fmin);
        Transcript transcript = getTranscript(exon)
        if (transcript != null && fmin < transcript.getFmin()) {
            transcriptService.setFmin(transcript, fmin);
        }
    }

    public void setFmax(Exon exon, Integer fmax) {
        exon.getFeatureLocation().setFmax(fmax);
        Transcript transcript = getTranscript(exon)
        if (transcript != null && fmax > transcript.getFmax()) {
            transcriptService.setFmax(transcript, fmax)
        }
    }


//    , String splitExonUniqueName
    public Exon makeIntron(Exon exon, int genomicPosition, int minimumIntronSize) {
        String sequence = sequenceService.getResiduesFromFeature(exon)
        int exonPosition = featureService.convertSourceCoordinateToLocalCoordinate(exon,genomicPosition);
//        // find donor coordinate
        String donorSite = null;
        int donorCoordinate = -1;
        for(String donor : SequenceTranslationHandler.spliceDonorSites){
            int coordinate = sequence.substring(0, exonPosition - minimumIntronSize).lastIndexOf(donor);
            if (coordinate > donorCoordinate) {
                donorCoordinate = coordinate;
                donorSite = donor;
            }
        }
//        // find acceptor coordinate
        String acceptorSite = null;
        int acceptorCoordinate = -1;
        for (String acceptor : SequenceTranslationHandler.getSpliceAcceptorSites()) {
            int coordinate = sequence.substring(exonPosition + minimumIntronSize, sequence.length()).indexOf(acceptor);
            if (acceptorCoordinate == -1 || coordinate < acceptorCoordinate) {
                acceptorCoordinate = coordinate;
                acceptorSite = acceptor;
            }
        }
//        // no donor/acceptor found
        if (donorCoordinate == -1 || acceptorCoordinate == -1 || (acceptorCoordinate - donorCoordinate) == 1) {
            //return splitExon(exon, genomicPosition - 1, genomicPosition + 1, splitExonUniqueName);
            return null;
        }
        acceptorCoordinate += exonPosition + minimumIntronSize;
        FeatureLocation exonFeatureLocation = exon.featureLocation
        if (exonFeatureLocation.getStrand().equals(-1)) {
            int tmp = acceptorCoordinate;
            acceptorCoordinate = donorCoordinate + 1 - donorSite.length();
            donorCoordinate = tmp + 1;
        } else {
            acceptorCoordinate += acceptorSite.length();
        }
        Exon splitExon = splitExon(exon, featureService.convertLocalCoordinateToSourceCoordinate(exon,donorCoordinate) , featureService.convertLocalCoordinateToSourceCoordinate(exon,acceptorCoordinate));

        exon.save()
        splitExon.save()

        return splitExon;
    }
//

    List<Exon> getSortedExons(Transcript transcript,boolean sortByStrand = true ) {
        List<Exon> sortedExons= new LinkedList<Exon>(transcriptService.getExons(transcript));
        Collections.sort(sortedExons,new FeaturePositionComparator<Exon>(sortByStrand))
        return sortedExons
    }

    /**
     * Set exon boundaries.
     *
     * @param exon - Exon to be modified
     * @param fmin - New fmin to be set
     * @param fmax - New fmax to be set
     */
    public void setExonBoundaries(Exon exon, int fmin, int fmax) {

        Transcript transcript = getTranscript(exon)
//        Transcript transcript = exon.getTranscript();
        exon.getFeatureLocation().fmin = fmin;
        exon.getFeatureLocation().fmax = fmax;
        featureService.removeExonOverlapsAndAdjacencies(transcript);

        featureService.updateGeneBoundaries(transcriptService.getGene(transcript));
    }

    def setToDownstreamDonor(Exon exon) {
        Transcript transcript = getTranscript(exon)
        Gene gene = transcriptService.getGene(transcript)

        List<Exon> exons = getSortedExons(transcript,true)

        Integer nextExonFmin = null;
        Integer nextExonFmax = null;
        for (ListIterator<Exon> iter = exons.listIterator(); iter.hasNext(); ) {
            Exon e = iter.next();
            if (e.getUniqueName().equals(exon.getUniqueName())) {
                if (iter.hasNext()) {
                    Exon e2 = iter.next();
                    nextExonFmin = e2.getFeatureLocation().getFmin();
                    nextExonFmax = e2.getFeatureLocation().getFmax();
                    break;
                }
            }
        }
        FeatureLocation exonFeatureLocation = FeatureLocation.findByFeature(exon)
        int coordinate = exonFeatureLocation.getStrand() == -1 ? featureService.convertSourceCoordinateToLocalCoordinate(gene,exonFeatureLocation.fmin) + 2 : featureService.convertSourceCoordinateToLocalCoordinate(gene,exonFeatureLocation.fmax) + 1;
        String residues = sequenceService.getResiduesFromFeature(gene)
        while (coordinate < residues.length()) {
            int c = featureService.convertLocalCoordinateToSourceCoordinate(gene,coordinate);
            if (nextExonFmin != null && (c >= nextExonFmin && c <= nextExonFmax + 1)) {
                throw new AnnotationException("Cannot set to downstream donor - will overlap next exon");
            }
            String seq = residues.substring(coordinate, coordinate + 2);

            if (SequenceTranslationHandler.getSpliceDonorSites().contains(seq)) {
                if (exon.getFeatureLocation().getStrand() == -1) {
                    setExonBoundaries(exon,featureService.convertLocalCoordinateToSourceCoordinate(gene,coordinate)+1,exon.getFeatureLocation().getFmax())
                } else {
                    setExonBoundaries(exon,exon.getFeatureLocation().getFmin(),featureService.convertLocalCoordinateToSourceCoordinate(gene,coordinate))
                }
                return;
            }
            ++coordinate;
        }
    }

    def setToUpstreamDonor(Exon exon) {
        Transcript transcript = getTranscript(exon)
        Gene gene = transcriptService.getGene(transcript)

        FeatureLocation exonFeatureLocation = FeatureLocation.findByFeature(exon)
        int coordinate = exonFeatureLocation.getStrand() == -1 ? featureService.convertSourceCoordinateToLocalCoordinate(gene,exonFeatureLocation.fmin) + 2 : featureService.convertSourceCoordinateToLocalCoordinate(gene,exonFeatureLocation.fmax) + 1;
        int exonStart = exonFeatureLocation.getStrand() == -1 ? featureService.convertSourceCoordinateToLocalCoordinate(gene,exon.getFmax()) - 1 : featureService.convertSourceCoordinateToLocalCoordinate(gene,exon.getFmin());
        String residues = sequenceService.getResiduesFromFeature(gene)
        while (coordinate > 0 ) {

            if (coordinate <= exonStart) {
                throw new AnnotationException("Cannot set to upstream donor - will remove exon");
            }
            String seq = residues.substring(coordinate, coordinate + 2);

//            println "seq ${seq} in ${SequenceTranslationHandler.spliceDonorSites}"
            if (SequenceTranslationHandler.getSpliceDonorSites().contains(seq)) {
                if (exon.getStrand() == -1) {
                    setExonBoundaries(exon, featureService.convertLocalCoordinateToSourceCoordinate(gene,coordinate) + 1, exon.getFmax());
                } else {
                    setExonBoundaries(exon, exon.getFmin(), featureService.convertLocalCoordinateToSourceCoordinate(gene,coordinate));
                }
                return;
            }
            --coordinate;
        }
    }

    def setToUpstreamAcceptor(Exon exon) {
        Transcript transcript = getTranscript(exon);
        Gene gene = transcriptService.getGene(transcript);

        List<Exon> exons = getSortedExons(transcript,true)
        Integer prevExonFmin = null;
        Integer prevExonFmax = null;
        for (ListIterator<Exon> iter = exons.listIterator(); iter.hasNext(); ) {
            Exon e = iter.next();
            if (e.getUniqueName().equals(exon.getUniqueName())) {
                if (iter.hasPrevious()) {
                    iter.previous();
                    if (iter.hasPrevious()) {
                        Exon e2 = iter.previous();
                        prevExonFmin = e2.getFmin();
                        prevExonFmax = e2.getFmax();
                    }
                }
                break;
            }
        }
        int coordinate = exon.getStrand() == -1 ? featureService.convertSourceCoordinateToLocalCoordinate(gene,exon.getFmax() + 2) : featureService.convertSourceCoordinateToLocalCoordinate(gene,exon.getFmin() - 3);
        String residues = sequenceService.getResiduesFromFeature(gene)
        while (coordinate >= 0) {
            int c = featureService.convertLocalCoordinateToSourceCoordinate(gene,coordinate);
            if (prevExonFmin != null && (c >= prevExonFmin && c <= prevExonFmax - 2)) {
                throw new AnnotationException("Cannot set to upstream acceptor - will overlap previous exon");
            }
            String seq = residues.substring(coordinate, coordinate + 2);
            if (SequenceTranslationHandler.getSpliceAcceptorSites().contains(seq)) {
                if (exon.getStrand() == -1) {
                    setExonBoundaries(exon, exon.getFmin(), featureService.convertLocalCoordinateToSourceCoordinate(gene,coordinate) - 1);
                } else {
                    setExonBoundaries(exon, featureService.convertLocalCoordinateToSourceCoordinate(gene,coordinate) + 2, exon.getFmax());
                }
                return;
            }
            --coordinate;
        }

    }

    def setToDownstreamAcceptor(Exon exon) {
        Transcript transcript = getTranscript(exon);
        Gene gene = transcriptService.getGene(transcript);
        int coordinate = exon.getStrand() == -1 ? featureService.convertSourceCoordinateToLocalCoordinate(gene,exon.getFmax()) : featureService.convertSourceCoordinateToLocalCoordinate(gene,exon.getFmin());
        int exonEnd = exon.getStrand() == -1 ? featureService.convertSourceCoordinateToLocalCoordinate(gene,exon.getFmin()) : featureService.convertSourceCoordinateToLocalCoordinate(gene,exon.getFmax()) - 1;
        String residues = sequenceService.getResiduesFromFeature(gene);
        while (coordinate < residues.length()) {
            if (coordinate >= exonEnd) {
                throw new AnnotationException("Cannot set to downstream acceptor - will remove exon");
            }
            String seq = residues.substring(coordinate, coordinate + 2);
            if (SequenceTranslationHandler.getSpliceAcceptorSites().contains(seq)) {
                if (exon.getStrand() == -1) {
                    setExonBoundaries(exon, exon.getFmin(), featureService.convertLocalCoordinateToSourceCoordinate(gene,coordinate) - 1);
                } else {
                    setExonBoundaries(exon, featureService.convertLocalCoordinateToSourceCoordinate(gene,coordinate) + 2, exon.getFmax());
                }
                return;
            }
            ++coordinate;
        }

    }

    Exon splitExon(Exon exon, int newLeftMax, int newRightMin) {
        Exon leftExon = exon;
        FeatureLocation leftFeatureLocation = leftExon.getFeatureLocation()

        String uniqueName = nameService.generateUniqueName(exon)
        Exon rightExon = new Exon(
                uniqueName: uniqueName
                ,name: uniqueName
                ,isAnalysis: leftExon.isAnalysis
                ,isObsolete: leftExon.isObsolete
        ).save(insert:true)

        
        FeatureLocation rightFeatureLocation = new FeatureLocation(
                feature: rightExon
                ,fmin: leftFeatureLocation.fmin
                ,isFminPartial: leftFeatureLocation.isFminPartial
                ,fmax: leftFeatureLocation.fmax
                ,isFmaxPartial: leftFeatureLocation.isFmaxPartial
                ,strand: leftFeatureLocation.strand
                ,phase: leftFeatureLocation.phase
                ,residueInfo: leftFeatureLocation.residueInfo
                ,locgroup: leftFeatureLocation.locgroup
                ,rank: leftFeatureLocation.rank
                ,sequence: leftFeatureLocation.sequence
        ).save(insert:true)
        rightExon.addToFeatureLocations(rightFeatureLocation)

        leftFeatureLocation.fmax = newLeftMax
        rightFeatureLocation.fmin = newRightMin
        
        leftFeatureLocation.save()
        rightFeatureLocation.save()

///
        Transcript transcript = getTranscript(leftExon)
        transcriptService.addExon(transcript,rightExon)

        transcript.save()
        rightExon.save()

        return rightExon

    }
    
    // added while working on getSequence() on 03.11.15 by D.U.
//    String getCodingSequenceInPhase(Exon exon, boolean removePartialCodons) {
//        Transcript transcript = getTranscript(exon)
//        CDS cds = transcriptService.getCDS(transcript)
//        if (cds == null || !exon.overlaps(cds)) {
//            return ""
//        }
//        int length = 0
//        FlankingRegion flankingRegion = new FlankingRegion(
//                uniqueName: "tmpFlankRegion"
//                ,isAnalysis: false
//                ,isObsolete: false
//                ,dateCreated: null
//        )
//
//        List <Exon> exons = transcriptService.getSortedExons(transcriptService.getExons(transcript))
//        for (Exon e in exons) {
//            if (e.equals(exon)) {
//                break // WHAT?
//            }
//            if (!e.overlaps(cds)) {
//                continue
//            }
//            int fmin = e.getFmin() < cds.getFmin() ? cds.getFmin() : e.getFmin()
//            int fmax = e.getFmax() > cds.getFmax() ? cds.getFmax() : e.getFmax()
//
//            FeatureLocation flankingRegionLocation = new FeatureLocation(
//                    feature: flankingRegion
//                    ,fmin: fmin
//                    ,fmax: fmax
//                    ,
//
//            )
//        }
//    }
    
    // Legacy code
//    private String getCodingSequenceInPhase(AnnotationEditor editor, Exon exon, boolean removePartialCodons) {
//        Transcript transcript = exon.getTranscript();
//        CDS cds = transcript.getCDS();
//        if (cds == null || !exon.overlaps(cds)) {
//            return "";
//        }
//        int length = 0;
//        FlankingRegion flankingRegion = new FlankingRegion(null, null, false, false, null, exon.getConfiguration());
//        flankingRegion.setFeatureLocation(new FeatureLocation());
//        flankingRegion.getFeatureLocation().setSourceFeature(exon.getFeatureLocation().getSourceFeature());
//        flankingRegion.setStrand(exon.getStrand());
//        List<Exon> exons = BioObjectUtil.createSortedFeatureListByLocation(transcript.getExons(), true);
//        for (Exon e : exons) {
//            if (e.equals(exon)) {
//                break;
//            }
//            if (!e.overlaps(cds)) {
//                continue;
//            }
//            int fmin = e.getFmin() < cds.getFmin() ? cds.getFmin() : e.getFmin();
//            int fmax = e.getFmax() > cds.getFmax() ? cds.getFmax() : e.getFmax();
//            flankingRegion.setFmin(fmin);
//            flankingRegion.setFmax(fmax);
//            length += editor.getSession().getResiduesWithAlterationsAndFrameshifts(flankingRegion).length();
//        }
//        flankingRegion.setFmin(exon.getFmin() < cds.getFmin() ? cds.getFmin() : exon.getFmin());
//        flankingRegion.setFmax(exon.getFmax() > cds.getFmax() ? cds.getFmax() : exon.getFmax());
//        String residues = editor.getSession().getResiduesWithAlterationsAndFrameshifts(flankingRegion);
//        if (removePartialCodons) {
//            int phase = length % 3 == 0 ? 0 : 3 - (length % 3);
//            residues = residues.substring(phase);
//            residues = residues.substring(0, residues.length() - (residues.length() % 3));
//        }
//        return residues;
//    }
}
