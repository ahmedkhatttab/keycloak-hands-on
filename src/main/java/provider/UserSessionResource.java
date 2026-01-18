package provider;

import jakarta.ws.rs.*;
import org.jboss.logging.Logger;
import org.keycloak.common.util.Time;
import org.keycloak.models.*;

import java.util.Map;
import java.util.stream.Stream;

public class UserSessionResource {
    private static final Logger log = Logger.getLogger(UserSessionResource.class);
    private final KeycloakSession session;
    private final RealmModel realm;
//    private final AdminPermissionEvaluator auth;

    public UserSessionResource(KeycloakSession session) {
        this.session = session;
        this.realm = session.getContext().getRealm();
    }

    private static SessionRepresentationDto toRepresentation(UserSessionModel session) {
        SessionRepresentationDto rep = new SessionRepresentationDto();
        rep.setId(session.getId());
        rep.setStart(Time.toMillis(session.getStarted()));
        rep.setLastAccess(Time.toMillis(session.getLastSessionRefresh()));
        rep.setUsername(session.getUser().getUsername());
        rep.setUserId(session.getUser().getId());
        for (AuthenticatedClientSessionModel clientSession : session.getAuthenticatedClientSessions().values()) {
            ClientModel client = clientSession.getClient();
            rep.getClients().put(client.getId(), client.getClientId());
        }
        return rep;
    }

    @Path("")
    @GET
    @Produces({"application/json"})
    public Stream<SessionRepresentationDto> realmSessions(@QueryParam("first") @DefaultValue("0") int first,
                                                          @QueryParam("max") @DefaultValue("10") int max) {
        log.infov(">>>>> realmSessions");
        final Map<String, Long> clientSessionStats = session.sessions().getActiveClientSessionStats(realm, false);
        log.infov(">>>>> clientSessionStats = {0}", clientSessionStats);
        clientSessionStats.entrySet().stream().peek(entry -> {
            log.infov("KEY {0} : VALUE {1}", entry.getKey(), entry.getValue());
        });
        Stream<SessionRepresentationDto> sessionRepresentationDtoStream = Stream.empty();
        for (Map.Entry<String, Long> entry : clientSessionStats.entrySet()) {
            ClientModel clientModel = realm.getClientById(entry.getKey());
            sessionRepresentationDtoStream = Stream.concat(sessionRepresentationDtoStream,
                    session.sessions().getUserSessionsStream(realm, clientModel)
                            .map(UserSessionResource::toRepresentation));
        }
        return applySearch("", sessionRepresentationDtoStream).distinct().skip(first).limit(max);
    }

    private Stream<SessionRepresentationDto> applySearch(String search, Stream<SessionRepresentationDto> result) {
//        result = result.filter(sessionRep ->
//                auth.users().canView(session.users().getUserById(realm, sessionRep.getUserId()))
//        );
//        if (!StringUtil.isBlank(search)) {
//            String searchTrimmed = search.trim();
//            result = result.filter(s -> s.getUsername().contains(searchTrimmed) || s.getIpAddress().contains(searchTrimmed)
//                    || s.getClients().values().stream().anyMatch(c -> c.contains(searchTrimmed)));
//        }
        return result;
    }
}
