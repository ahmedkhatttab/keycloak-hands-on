package required.action;

import org.jboss.logging.Logger;
import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;

public class UserSurveyRequiredActionProvider implements RequiredActionProvider {

    private static final String SUREVEY_RESULT = "survey_result";
    Logger log = Logger.getLogger(UserSurveyRequiredActionProvider.class);

    // add required action to user / called during user login
    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        // condition to determine if we need to add required action to the user or not
        // user-survey-required-action = id of the providerFactory
        String doSurvey = context.getUser().getFirstAttribute(SUREVEY_RESULT);
        log.infov("survey_result >>>>>>> {0}", doSurvey);
//        LocalTime timeToCheck = LocalTime.now(ZoneId.of("Asia/Riyadh"));
//        LocalTime cutoff = LocalTime.of(23, 17); // 10:35 PM
//        log.infov(">>>>> timeToCheck: {0}", timeToCheck);
//        if (timeToCheck.isBefore(cutoff)) {
        if (doSurvey == null || doSurvey.isBlank()) {
            log.infov(">>>>> user needs to do the survey");
            context.getUser().addRequiredAction("user-survey-required-action");
        }
    }

    // display challenge to user
    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        context.challenge(context.form().createForm("survey-form.ftl"));
    }

    // handle submitted data
    @Override
    public void processAction(RequiredActionContext context) {
        // remove required action because it is an optional action, user can do or ignore it.
        context.getUser().removeRequiredAction("user-survey-required-action");
        String cancel = context.getHttpRequest()
                .getDecodedFormParameters()
                .getFirst("cancel");

        if (cancel != null) {
            log.info(">>>>> User PRESSED CANCEL");
            context.getUser().setSingleAttribute(SUREVEY_RESULT, "IGNORED BY USER");
            context.success();
            return;
        }
        log.info(">>>>> User PRESSED ACCEPT");
        String surveyInformation = context.getHttpRequest()
                .getDecodedFormParameters()
                .getFirst("surveyInformation");

        log.infov("surveyInformation >>>>> {0}", surveyInformation);
        if (surveyInformation != null && !surveyInformation.isBlank()) {
            context.getUser().setSingleAttribute(SUREVEY_RESULT, surveyInformation);
        }
        context.success();
    }

//    @Override
//    public void processAction(RequiredActionContext context) {
//        String cancel = context.getHttpRequest().getDecodedFormParameters().getFirst("cancel");
//        context.getUser().removeRequiredAction("user-survey-required-action");
//        if (cancel != null) {
//            log.info(">>>>> User PRESSED CANCEL");
////            context.getUser().removeRequiredAction("user-survey-required-action");
//            context.success();
//            return;
//        }
//        log.info(">>>>> User PRESSED ACCEPT");
//
//        String surveyInformation = context.getHttpRequest().getDecodedFormParameters().getFirst("surveyInformation");
//        // get keyclock jpa provider to store value in user model as attribute
//        EntityManager em = context.getSession().getProvider(JpaConnectionProvider.class).getEntityManager();
//        UserAttributeEntity userAttributeEntity = new UserAttributeEntity();
//        userAttributeEntity.setId(UUID.randomUUID().toString());
//        userAttributeEntity.setName("survey-result");
//        userAttributeEntity.setValue(surveyInformation);
//        UserEntity user = new UserEntity();
//        user.setId(context.getUser().getId());
//        userAttributeEntity.setUser(user);
//        em.persist(userAttributeEntity);

    /// /        context.getUser().removeRequiredAction("user-survey-required-action");
//        context.success();
//    }
    @Override
    public void close() {

    }
}
