<%@include file="/WEB-INF/bootstrap.jsp" %>
<c:set var="_page_current" value="contact" />
<%@include file="/WEB-INF/views/fragments/header.jspf" %>
    <div class="page-header">
        <h1>Contact</h1>
    </div>
    <p class="text-justify">
        L'auteur du site web de gestion de la médiathèque de Polytech Lyon est
        <a href="mailto: bruno.buiret@etu.univ-lyon1.fr">Bruno Buiret</a>. Il a été
        créé au cours de l'année 2015 - 2016 dans le cadre de l'<abbr title="Unité d'enseignement">UE</abbr>
        de <strong>programmation répartie</strong> à l'aide des technologies suivantes :
    </p>
    <ul>
        <li>
            Langage <strong>Java</strong> ;
        </li>
        <li>
            Serveur <strong>Apache Tomcat</strong> ;
        </li>
        <li>
            Gestionnaire <strong>Apache Maven</strong> ;
        </li>
        <li>
            Framework <strong>Spring</strong> et <strong>Hibernate</strong>.
        </li>
    </ul>
<%@include file="/WEB-INF/views/fragments/footer.jspf" %>