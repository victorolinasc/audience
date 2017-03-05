//package br.com.concretesolutions.audience.sample.data.service;
//
//import br.com.concretesolutions.audience.core.actor.ActorRef;
//import br.com.concretesolutions.audience.core.actor.SingletonActor;
//import br.com.concretesolutions.audience.sample.SampleApplication;
//import br.com.concretesolutions.audience.sample.data.api.Api;
//import br.com.concretesolutions.audience.sample.data.api.ApiActor;
//import br.com.concretesolutions.audience.sample.data.persistence.EntityList;
//import br.com.concretesolutions.audience.sample.data.persistence.entity.RepositoryEntity;
//
//public final class RepositoryService implements SingletonActor {
//
//    private static final int ITEMS_PER_PAGE = 10;
//
//    public static class Page {
//
//        final int page;
//
//        public Page(int page) {
//            this.page = page;
//        }
//    }
//
//    private final SampleApplication app;
//
//    public RepositoryService(SampleApplication app) {
//        this.app = app;
//    }
//
//    @Override
//    public void onActorRegistered(ActorRef thisRef) {
//        thisRef.passScript(Page.class, this::handlePage);
//    }
//
//    void handlePage(Page page, ActorRef sender, ActorRef self) {
//
//        // fetch API asynchronously
////        self.tell()
////                .toActor(ApiActor.class);
//
//        ApiActor.sendCall(self, api ->
//                api.getRepositories("language:java", Api.Sort.stars, page.page,
//                        RepositoryService.ITEMS_PER_PAGE));
//
//        final EntityList<RepositoryEntity> repositories = getRepositories(page.page);
//
//        if (repositories.isEmpty())
//            return; // wait for API
//
//        // deliver cache
//        self.tell(repositories)
//                .onStage()
//                .to(sender);
//    }
//
//    private EntityList<RepositoryEntity> getRepositories(int page) {
//
//        return app.getDatabase()
//                .select(RepositoryEntity.class)
//                .orderBy(RepositoryEntity.STARGAZERS_COUNT)
//                .limit(ITEMS_PER_PAGE)
//                .offset(page * ITEMS_PER_PAGE)
//                .get()
//                .collect(new EntityList<>());
//    }
//}
