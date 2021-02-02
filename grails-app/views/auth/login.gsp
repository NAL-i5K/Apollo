<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="login"/>
    <title>Login</title>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <!-- USWDS Design Systenm -->
<!--
    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>
    <asset:link rel="shortcut icon" href="webapollo_favicon.ico" type="image/x-icon"/>
    <asset:image src="ApolloLogo_100x36.png" alt="Web Apollo"/></a>

-->
    <asset:stylesheet src="uswds.min.css"/>
    <asset:stylesheet src="nal.css"/>
    <asset:javascript src="uswds.min.js"/>

    <!-- Source Sans Pro - Google Font -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@200;300;400;600;700&display=swap" rel="stylesheet">
    

</head>
<body>
  <div class="usa-overlay"></div>
    <!-- Government Banner -->
    <section class="usa-banner" aria-label="Official government website">
        <div class="usa-accordion">
            <header class="usa-banner__header">
                <div class="usa-banner__inner">
                    <div class="grid-col-auto">
			<asset:image src="us_flag_small.png" alt="U.S. flag" class="usa-banner__header-flag"/>
                    </div>
                    <div class="grid-col-fill tablet:grid-col-auto">
                        <p class="usa-banner__header-text">An official website of the United States government</p>
                        <p class="usa-banner__header-action" aria-hidden="true">Here’s how you know</p>
                    </div>
                    <button class="usa-accordion__button usa-banner__button" aria-expanded="false"
                        aria-controls="gov-banner">
                        <span class="usa-banner__button-text">Here’s how you know</span>
                    </button>
                </div>
            </header>
            <div class="usa-banner__content usa-accordion__content" id="gov-banner">
                <div class="grid-row grid-gap-lg">
                    <div class="usa-banner__guidance tablet:grid-col-6">
                        <asset:image class="usa-banner__icon usa-media-block__img" src="icon-dot-gov.svg" role="img"  alt="Dot gov"/>
                        <div class="usa-media-block__body">
                            <p>
                                <strong>
                                    Official websites use .gov
                                </strong>
                                <br />
                                A <strong>.gov</strong> website belongs to an official government organization in the United
                                States.

                            </p>
                        </div>
                    </div>
                    <div class="usa-banner__guidance tablet:grid-col-6">
                        <asset:image class="usa-banner__icon usa-media-block__img" src="icon-https.svg" role="img" alt="Https"/>
                        <div class="usa-media-block__body">
                            <p>
                                <strong>
                                    Secure .gov websites use HTTPS
                                </strong>
                                <br />
                                A <strong>lock</strong> (
                                <span class="icon-lock"><svg xmlns="http://www.w3.org/2000/svg" width="52" height="64"
                                        viewBox="0 0 52 64" class="usa-banner__lock-image" role="img"
                                        aria-labelledby="banner-lock-title banner-lock-description">
                                        <title id="banner-lock-title">Lock</title>
                                        <desc id="banner-lock-description">A locked padlock</desc>
                                        <path fill="#000000" fill-rule="evenodd"
                                            d="M26 0c10.493 0 19 8.507 19 19v9h3a4 4 0 0 1 4 4v28a4 4 0 0 1-4 4H4a4 4 0 0 1-4-4V32a4 4 0 0 1 4-4h3v-9C7 8.507 15.507 0 26 0zm0 8c-5.979 0-10.843 4.77-10.996 10.712L15 19v9h22v-9c0-6.075-4.925-11-11-11z" />
                                    </svg></span>
                                ) or <strong>https://</strong> means you’ve safely connected to the .gov website. Share
                                sensitive information only on official, secure websites.

                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- End Government Banner -->
 <!-- Start NAL Header -->
  <header class="usa-header usa-header--extended">
    <div class="usa-navbar">
      <div class="usa-logo nal-signature" id="extended-logo">
        <asset:image class="nal-signature__usda-logo" src="usda-symbol.svg" />
        <div class="nal-signature__text">
          <div class="nal-signature__site-name">
            <a href="../" title="Home" rel="home">i5k Workspace@NAL</a>
          </div>
          <div class="nal-signature__usda">
            <a href="https://www.usda.gov">U.S. Department of Agriculture</a>
          </div>
        </div>
      </div>
      <button class="usa-menu-btn">Menu</button>
    </div>
    <nav aria-label="Primary navigation" class="usa-nav">
      <div class="usa-nav__inner">
        <button class="usa-nav__close">
          <img src="../img/close.svg" role="img" alt="close">
        </button>
        <ul class="usa-nav__primary usa-accordion">
        </ul>
        <div class="usa-nav__secondary">
          <ul class="usa-nav__secondary-links">
            <li class="usa-nav__secondary-item">
              <a href="https://www.nal.usda.gov/main/ask-question">Ask A Question</a>
            </li>
            <li class="usa-nav__secondary-item">
              <a href="https://www.nal.usda.gov/main/contact-us">Contact Us</a>
            </li>
            <li class="usa-nav__secondary-item">
              <a href="https://www.nal.usda.gov/main/about-us/visit-library">Visit the Library</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </header>
</div>
<!-- End NAL Header -->
<div>
        <div class="content" style="margin: 30px; float:left; font-size:12px">
          <br><p></p>
          <p style="text-align:center"><strong>Upon Login You Agree to the Following Information</strong></p>
          <ul>
            <li>You are accessing a U.S. Government information system, which includes (1) this computer, (2) this computer network, (3) all computers connected to this network, and (4) all devices and storage media attached to this network or to a computer on this network. This information system is provided for U.S. Government authorized use only.</li>
            <li>Unauthorized or improper use of this system may result in disciplinary action, as well as civil and criminal penalties.</li>
            <li>By using this information system, you understand and consent to the following:
          <ol>
            <li>You have no reasonable expectation of privacy regarding any communications or data transmittal or stored on this information system. At any time, the government may for any lawful government purpose monitor, intercept, search and seize and communication or data transmission or storage on this information system.</li>
            <li>Any communications or data transmitting or storing on this information system may be disclosed or used for any lawful government purpose.</li>
            <li>Your consent is final and irrevocable. You may not rely on any statements or informal policies purporting to provide you with any expectation of privacy regarding communications on this system, where oral or written, by your supervisor or any other official, except USDA's Chief Information Officer.</li>
         </ol>
         </li>
          </ul>
     </div>

    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:form action="signIn">
        <input type="hidden" name="targetUri" value="${targetUri}" class="col-md-4"/>
        <div class="col-md-5 col-lg-offset-1" style="margin-top: 10px;">
            %{--<input type="username" class="form-control" placeholder="Username (email)" required autofocus>--}%
            %{--<input type="password" class="form-control" placeholder="Password" required>--}%
            %{--<button class="btn btn-lg btn-primary btn-block" type="submit">--}%
            %{--Sign in</button>--}%
            %{--<label class="checkbox pull-left">--}%
            %{--<input type="checkbox" value="${rememberMe}">--}%
            %{--Remember me--}%
            %{--</label>--}%
            %{--<table>--}%
            %{--<tbody>--}%
            %{--<tr>--}%
            %{--<td>Username:</td>--}%
            %{--<td>--}%
            <input name="username" value="${username}" type="username" class="form-control col-md-4"
                   style="margin:10px;"
                   placeholder="Username (email)"
                   required autofocus/>
            %{--</td>--}%
            %{--</tr>--}%
            %{--<tr>--}%
            %{--<td>Password:</td>--}%
            %{--<td><input type="password" name="password" value="" /></td>--}%
            <input type="password" name="password" value="" class="form-control" placeholder="Password" required
                   style="margin:10px;"/>
            %{--</tr>--}%
            %{--<tr>--}%
            %{--<td>Remember me?:</td>--}%
            %{--<td><g:checkBox name="rememberMe" value="${rememberMe}" /></td>--}%
            %{--<div class="col-md-5 row" style="margin-bottom: 10px;">--}%
                <input class="col-md-4 col-md-offset-1 btn btn-lg btn-primary" type="submit"
                       value="Login">

                <label class="checkbox col-md-4" style="margin:10px;">
                    <g:checkBox name="rememberMe" value="${rememberMe}"/>
                    <input class="checkbox" type="checkbox" name="rememberMe" value="${rememberMe}"/>
                    Remember me
                </label>
            %{--</div>--}%

            %{--<div class="col-md-5 row">--}%
            %{--</div>--}%
            %{--</tr>--}%
            %{--<tr>--}%
            %{--<td />--}%
            %{--<input type="submit" value="Sign in"/>--}%
            %{--<button class="btn btn-lg btn-primary btn-block" type="submit" value="Login">--}%
            %{--<td><input type="submit" value="Sign in" /></td>--}%
            %{--</tr>--}%
            %{--</tbody>--}%
            %{--</table>--}%
        </div>
    </g:form>
    <!-- Footer -->
    <br><br><br>
    <div class="footer" role="contentinfo">    
      <footer class="usa-footer usa-footer--big">
        <div class="usa-footer__primary-section">
            <div class="grid-container">
                <div class="grid-row grid-gap flex-align-center">
                    <div class="tablet:grid-col-8">
                        <nav class="usa-footer__nav" aria-labelledby="footer-links-header">
                            <h2 id="footer-links-header" class="usa-sr-only">Footer</h2>
                            <ul class="display-flex flex-wrap">
                                <li class="nal-footer__primary-link">
                                    <a href="https://www.nal.usda.gov/main/">Home</a>
                                </li>
                                <li class="nal-footer__primary-link">
                                    <a href="https://www.nal.usda.gov/nal/sitemap">Site Map</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                    <nav class="tablet:grid-col-4" aria-labelledby="social-links-header">
                        <h2 id="social-links-header" class="usa-sr-only">Social Links</h2>
                        <ul class="grid-row grid-gap-1 usa-list usa-list--unstyled">
                            <li class="grid-col-auto">
                                <a class="usa-social-link usa-social-link--facebook" href="javascript:void(0);">
                                    <span>Facebook</span>
                                </a>
                            </li>
                            <li class="grid-col-auto">
                                <a class="usa-social-link usa-social-link--twitter" href="javascript:void(0);">
                                    <span>Twitter</span>
                                </a>
                            </li>
                            <li class="grid-col-auto">
                                <a class="usa-social-link usa-social-link--youtube" href="javascript:void(0);">
                                    <span>YouTube</span>
                                </a>
                            </li>
                            <li class="grid-col-auto">
                                <a class="usa-social-link usa-social-link--rss" href="javascript:void(0);">
                                    <span>RSS</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <div class="usa-footer__secondary-section">
            <div class="grid-container">
                <div class="grid-row grid-gap">
                    <div class="tablet:grid-col-8">
                        <nav class="usa-footer__nav" aria-labelledby="government-links-header">
                            <h2 id="government-links-header" class="usa-sr-only">Government Links</h2>
                            <ul>
                                <li class="usa-footer__secondary-link">
                                    <a href="https://www.usda.gov/policies-and-links">Policies and Links</a>
                                </li>
                                <li class="usa-footer__secondary-link">
                                    <a href="https://www.usda.gov/plain-writing">Plain Writing</a>
                                </li>
                                <li class="usa-footer__secondary-link">
                                    <a href="https://www.dm.usda.gov/foia/">FOIA</a>
                                </li>
                                <li class="usa-footer__secondary-link">
                                    <a href="https://www.usda.gov/accessibility-statement">Accessibility Statement</a>
                                </li>
                                <li class="usa-footer__secondary-link">
                                    <a href="https://www.usda.gov/privacy-policy">Privacy Policy</a></li>
                                <li class="usa-footer__secondary-link">
                                    <a href="https://www.usda.gov/non-discrimination-statement">Non-Discrimination Statement</a>
                                </li>
                                <li class="usa-footer__secondary-link">
                                    <a href="https://www.ascr.usda.gov/civil-rights-statements">Anti-Harassment Policy</a>
                                </li>
                                <li class="usa-footer__secondary-link">
                                    <a href="https://www.ars.usda.gov/docs/quality-of-information/">Information Quality</a>
                                </li>
                                <li class="usa-footer__secondary-link">
                                    <a href="https://www.ars.usda.gov">Agricultural Research Service</a>
                                </li>
                                <li class="usa-footer__secondary-link">
                                    <a href="https://www.usda.gov">USDA.gov</a>
                                </li>
                                <li class="usa-footer__secondary-link">
                                    <a href="https://www.usa.gov">USA.gov</a>
                                </li>
                                <li class="usa-footer__secondary-link">
                                    <a href="https://www.whitehouse.gov">WhiteHouse.gov</a></li>
                            </ul>
                        </nav>
                    </div>
                    <div class="tablet:grid-col-4">
                        <div class="usa-sign-up">
                            <h3 class="usa-sign-up__heading">Sign up for ARS News updates</h3>
                            <form class="usa-form" action="https://public.govdelivery.com/accounts/USDAARS/subscriber/qualify?qsp=CODE_RED">
                                <input name="utf8" type="hidden" value="✓">
                                <input type="hidden" name="authenticity_token" value="tV2OquJR5xnmtrmmZS3UWsIp7QddNiZcKotw2AMMUx2u9nfu4b3aL1Fb4L6RnJCoF5VYhXZ85qUPjpOyJiUlhg==">
                                <label class="usa-label" for="email" id="">Your email address</label>
                                <input class="usa-input" id="email" name="email" type="email">
                                <button class="usa-button" type="submit">Sign up</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </footer>
      </div>
</body>
</html>
