# hibernate-search-bug-composite-keys
Demo project to demonstrate a possible bug in Hibernate Search

```java
@Data
@NoArgsConstructor
@Entity
@Indexed
public class Person {
	@Id
	private Long id;

	@FullTextField
	private String name;

	@OneToOne
	@IndexedEmbedded
	private Task task;
}

@Data
@NoArgsConstructor
@Entity
@IdClass(TaskKey.class)
public class Task {
	@Id
	private String code;
	@Id
	private String reference;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskKey {
	private String code;
	private String reference;
}
```

With these simple entities, I get this error :

<details>
<summary>See logs</summary>

```log
2024-11-08T13:58:40.150+01:00  INFO 4260 --- [           main] b.i.HibernateSearchPreIntegrationService : HSEARCH000034: Hibernate Search version 7.1.2.Final
2024-11-08T13:58:41.009+01:00  INFO 4260 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
2024-11-08T13:58:41.804+01:00 ERROR 4260 --- [           main] j.LocalContainerEntityManagerFactoryBean : Failed to initialize JPA EntityManagerFactory: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.search.util.common.SearchException: HSEARCH000520: Hibernate Search encountered failures during bootstrap. Failures:

    Hibernate ORM mapping: 
        type 'org.test.hibernate.entities.Person': 
            path '.task<no value extractors>': 
                failures: 
                  - HSEARCH700078: No readable property named '_identifierMapper' on type 'org.test.hibernate.entities.Task'.
2024-11-08T13:58:41.805+01:00  WARN 4260 --- [           main] ConfigServletWebServerApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.search.util.common.SearchException: HSEARCH000520: Hibernate Search encountered failures during bootstrap. Failures:

    Hibernate ORM mapping: 
        type 'org.test.hibernate.entities.Person': 
            path '.task<no value extractors>': 
                failures: 
                  - HSEARCH700078: No readable property named '_identifierMapper' on type 'org.test.hibernate.entities.Task'.
2024-11-08T13:58:41.805+01:00  INFO 4260 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2024-11-08T13:58:41.808+01:00  INFO 4260 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
2024-11-08T13:58:41.809+01:00  INFO 4260 --- [           main] o.apache.catalina.core.StandardService   : Stopping service [Tomcat]
2024-11-08T13:58:41.821+01:00  INFO 4260 --- [           main] .s.b.a.l.ConditionEvaluationReportLogger : 

Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2024-11-08T13:58:41.838+01:00 ERROR 4260 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.search.util.common.SearchException: HSEARCH000520: Hibernate Search encountered failures during bootstrap. Failures:

    Hibernate ORM mapping: 
        type 'org.test.hibernate.entities.Person': 
            path '.task<no value extractors>': 
                failures: 
                  - HSEARCH700078: No readable property named '_identifierMapper' on type 'org.test.hibernate.entities.Task'.
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1806) ~[spring-beans-6.1.14.jar:6.1.14]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:600) ~[spring-beans-6.1.14.jar:6.1.14]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:522) ~[spring-beans-6.1.14.jar:6.1.14]
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:337) ~[spring-beans-6.1.14.jar:6.1.14]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-6.1.14.jar:6.1.14]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:335) ~[spring-beans-6.1.14.jar:6.1.14]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:205) ~[spring-beans-6.1.14.jar:6.1.14]
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:954) ~[spring-context-6.1.14.jar:6.1.14]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:625) ~[spring-context-6.1.14.jar:6.1.14]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.3.5.jar:3.3.5]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:754) ~[spring-boot-3.3.5.jar:3.3.5]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:456) ~[spring-boot-3.3.5.jar:3.3.5]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:335) ~[spring-boot-3.3.5.jar:3.3.5]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1363) ~[spring-boot-3.3.5.jar:3.3.5]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1352) ~[spring-boot-3.3.5.jar:3.3.5]
	at org.test.hibernate.HibernateSearchBugCompositeKeysApplication.main(HibernateSearchBugCompositeKeysApplication.java:14) ~[classes/:na]
Caused by: jakarta.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is org.hibernate.search.util.common.SearchException: HSEARCH000520: Hibernate Search encountered failures during bootstrap. Failures:

    Hibernate ORM mapping: 
        type 'org.test.hibernate.entities.Person': 
            path '.task<no value extractors>': 
                failures: 
                  - HSEARCH700078: No readable property named '_identifierMapper' on type 'org.test.hibernate.entities.Task'.
	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:421) ~[spring-orm-6.1.14.jar:6.1.14]
	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396) ~[spring-orm-6.1.14.jar:6.1.14]
	at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:366) ~[spring-orm-6.1.14.jar:6.1.14]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1853) ~[spring-beans-6.1.14.jar:6.1.14]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1802) ~[spring-beans-6.1.14.jar:6.1.14]
	... 15 common frames omitted
Caused by: org.hibernate.search.util.common.SearchException: HSEARCH000520: Hibernate Search encountered failures during bootstrap. Failures:

    Hibernate ORM mapping: 
        type 'org.test.hibernate.entities.Person': 
            path '.task<no value extractors>': 
                failures: 
                  - HSEARCH700078: No readable property named '_identifierMapper' on type 'org.test.hibernate.entities.Task'.
	at org.hibernate.search.engine.reporting.spi.RootFailureCollector.checkNoFailure(RootFailureCollector.java:67) ~[hibernate-search-engine-7.1.2.Final.jar:7.1.2.Final]
	at org.hibernate.search.engine.common.impl.SearchIntegrationBuilder.prepareBuild(SearchIntegrationBuilder.java:191) ~[hibernate-search-engine-7.1.2.Final.jar:7.1.2.Final]
	at org.hibernate.search.mapper.orm.bootstrap.impl.HibernateSearchPreIntegrationService$NotBooted.doBootFirstPhase(HibernateSearchPreIntegrationService.java:279) ~[hibernate-search-mapper-orm-7.1.2.Final.jar:7.1.2.Final]
	at org.hibernate.search.mapper.orm.bootstrap.impl.HibernateOrmIntegrationBooterImpl.bootNow(HibernateOrmIntegrationBooterImpl.java:179) ~[hibernate-search-mapper-orm-7.1.2.Final.jar:7.1.2.Final]
	at java.base/java.util.concurrent.CompletableFuture$UniApply.tryFire(CompletableFuture.java:646) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:510) ~[na:na]
	at java.base/java.util.concurrent.CompletableFuture.complete(CompletableFuture.java:2179) ~[na:na]
	at org.hibernate.search.mapper.orm.bootstrap.impl.HibernateSearchSessionFactoryObserver.sessionFactoryCreated(HibernateSearchSessionFactoryObserver.java:41) ~[hibernate-search-mapper-orm-7.1.2.Final.jar:7.1.2.Final]
	at org.hibernate.internal.SessionFactoryObserverChain.sessionFactoryCreated(SessionFactoryObserverChain.java:35) ~[hibernate-core-6.6.2.Final.jar:6.6.2.Final]
	at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:324) ~[hibernate-core-6.6.2.Final.jar:6.6.2.Final]
	at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:463) ~[hibernate-core-6.6.2.Final.jar:6.6.2.Final]
	at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1506) ~[hibernate-core-6.6.2.Final.jar:6.6.2.Final]
	at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:75) ~[spring-orm-6.1.14.jar:6.1.14]
	at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:390) ~[spring-orm-6.1.14.jar:6.1.14]
	at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:409) ~[spring-orm-6.1.14.jar:6.1.14]
	... 19 common frames omitted
	Suppressed: org.hibernate.search.util.common.SearchException: HSEARCH700078: No readable property named '_identifierMapper' on type 'org.test.hibernate.entities.Task'.
		at org.hibernate.search.mapper.pojo.model.spi.AbstractPojoRawTypeModel.property(AbstractPojoRawTypeModel.java:113) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.model.path.impl.BoundPojoModelPathTypeNode.property(BoundPojoModelPathTypeNode.java:36) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.automaticindexing.building.impl.PojoAssociationPathInverter.findInverseSidePathFromInverseSideRecursive(PojoAssociationPathInverter.java:192) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.automaticindexing.building.impl.PojoAssociationPathInverter.findInverseSidePathFromInverseSide(PojoAssociationPathInverter.java:174) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.automaticindexing.building.impl.PojoAssociationPathInverter.invertPath(PojoAssociationPathInverter.java:65) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.automaticindexing.building.impl.PojoIndexingDependencyCollectorTypeNode.collectDependency(PojoIndexingDependencyCollectorTypeNode.java:183) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.automaticindexing.building.impl.PojoIndexingDependencyCollectorMonomorphicDirectValueNode.doCollectDependency(PojoIndexingDependencyCollectorMonomorphicDirectValueNode.java:93) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.automaticindexing.building.impl.PojoIndexingDependencyCollectorMonomorphicDirectValueNode.collectDependency(PojoIndexingDependencyCollectorMonomorphicDirectValueNode.java:66) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.processing.building.impl.PojoIndexingProcessorValueNodeBuilderDelegate.doBuild(PojoIndexingProcessorValueNodeBuilderDelegate.java:197) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.processing.building.impl.PojoIndexingProcessorValueNodeBuilderDelegate.build(PojoIndexingProcessorValueNodeBuilderDelegate.java:169) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.processing.building.impl.PojoIndexingProcessorPropertyNodeBuilder.doBuild(PojoIndexingProcessorPropertyNodeBuilder.java:178) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.processing.building.impl.PojoIndexingProcessorPropertyNodeBuilder.build(PojoIndexingProcessorPropertyNodeBuilder.java:156) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.processing.building.impl.AbstractPojoIndexingProcessorTypeNodeBuilder.lambda$doBuild$0(AbstractPojoIndexingProcessorTypeNodeBuilder.java:127) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197) ~[na:na]
		at java.base/java.util.Iterator.forEachRemaining(Iterator.java:133) ~[na:na]
		at java.base/java.util.Spliterators$IteratorSpliterator.forEachRemaining(Spliterators.java:1939) ~[na:na]
		at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509) ~[na:na]
		at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499) ~[na:na]
		at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151) ~[na:na]
		at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174) ~[na:na]
		at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234) ~[na:na]
		at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596) ~[na:na]
		at org.hibernate.search.mapper.pojo.processing.building.impl.AbstractPojoIndexingProcessorTypeNodeBuilder.doBuild(AbstractPojoIndexingProcessorTypeNodeBuilder.java:130) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.processing.building.impl.AbstractPojoIndexingProcessorTypeNodeBuilder.build(AbstractPojoIndexingProcessorTypeNodeBuilder.java:97) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.mapping.impl.PojoIndexedTypeManager$Builder.preBuildIndexingProcessor(PojoIndexedTypeManager.java:186) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.mapping.building.impl.PojoMapper.preBuildIndexingProcessorAndCollectDependencies(PojoMapper.java:390) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.mapper.pojo.mapping.building.impl.PojoMapper.prepareBuild(PojoMapper.java:305) ~[hibernate-search-mapper-pojo-base-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.engine.common.impl.SearchIntegrationBuilder$MappingBuildingState.partiallyBuildAndAddTo(SearchIntegrationBuilder.java:287) ~[hibernate-search-engine-7.1.2.Final.jar:7.1.2.Final]
		at org.hibernate.search.engine.common.impl.SearchIntegrationBuilder.prepareBuild(SearchIntegrationBuilder.java:188) ~[hibernate-search-engine-7.1.2.Final.jar:7.1.2.Final]
		... 32 common frames omitted


Process finished with exit code 1
```
</details>

From what I understand, '_identifierMapper' is a hidden field added by Hibernate since Hibernate 6 to entities which have a composite key.
