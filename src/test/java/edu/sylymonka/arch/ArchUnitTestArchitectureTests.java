package edu.sylymonka.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.library.Architectures;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.xml.validation.Schema;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@SpringBootTest
class ArchUnitTestArchitectureTests {

	private JavaClasses importedClasses;

	@BeforeEach
	void init(){
		importedClasses = new ClassFileImporter()
				.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
				.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
				.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
				.importPackages("edu.sylymonka.arch");
	}
	@Test
	void shouldFollowLayerArchitecture() {
		layeredArchitecture()
				.consideringAllDependencies()
				.layer("Controller").definedBy("..Controller..")
				.layer("Service").definedBy("..Service..")
				.layer("Repository").definedBy("..Repository..")
				.whereLayer("Controller").mayNotBeAccessedByAnyLayer()
				.whereLayer("Service").mayOnlyBeAccessedByLayers("Controller","Service")
				.whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
				.check(importedClasses);
	}

	@Test
	void repositoryClassesShouldBeNamedXRepository(){
		classes()
				.that().resideInAPackage("..Repository..")
				.should().haveSimpleNameEndingWith("Repository")
				.check(importedClasses);
	}
	@Test
	void repositoryShouldBeAnnotatedWithRepositoryAnnotation(){
		classes()
				.that().resideInAPackage("..Repository..")
				.should().beAnnotatedWith(Repository.class)
				.check(importedClasses);
	}@Test
	void repositoryShouldBeInterfaces(){
		classes()
				.that().resideInAPackage("..Repository..")
				.should().beInterfaces()
				.check(importedClasses);
	}
	@Test
	void repositoriesShouldExtendMongoRepositoryInterface(){
		classes()
				.that().resideInAPackage("..Repository..")
				.should().beInterfaces()
				.andShould().beAssignableTo(MongoRepository.class)
				.check(importedClasses);
	}
	@Test
	void repositoryClassesShouldNotAccessServiceClasses() {
		noClasses()
				.that().resideInAPackage("..Repository..")
				.should().dependOnClassesThat().resideInAnyPackage("..Service..")
				.check(importedClasses);
	}
	@Test
	void repositoryClassesShouldNotBeAnnotatedWithServiceAndControllerAnnotation() {
		noClasses()
				.that().resideInAPackage("..Repository..")
				.should().beAnnotatedWith(Service.class)
				.andShould().beAnnotatedWith(Controller.class)
				.andShould().beAnnotatedWith(RestController.class)
				.andShould().beAnnotatedWith(RequestMapping.class)
				.check(importedClasses);
	}


	@Test
	void serviceClassesShouldBeNamedXService(){
		classes()
				.that().resideInAPackage("..Service..")
				.should().haveSimpleNameEndingWith("Service")
				.check(importedClasses);
	}
	@Test
	void serviceShouldBeAnnotatedWithServiceAnnotation(){
		classes()
				.that().resideInAPackage("..Service..")
				.should().beAnnotatedWith(Service.class)
				.check(importedClasses);
	}
	@Test
	void serviceClassesShouldAccessRepositoryClasses() {
		classes()
				.that().resideInAPackage("..Service..")
				.should().dependOnClassesThat().resideInAnyPackage("..Repository..")
				.check(importedClasses);
	}
	@Test
	void serviceShouldNotDependOnController(){
		noClasses()
				.that().resideInAPackage("..Service..")
				.should().dependOnClassesThat().resideInAPackage("..Controller..")
				.check(importedClasses);
	}
	@Test
	void serviceClassesShouldNotBeAnnotatedWithRepositoryAndControllerAnnotations() {
		noClasses()
				.that().resideInAPackage("..Service..")
				.should().beAnnotatedWith(Repository.class)
				.andShould().beAnnotatedWith(Controller.class)
				.andShould().beAnnotatedWith(RestController.class)
				.andShould().beAnnotatedWith(RequestMapping.class)
				.check(importedClasses);
	}
	@Test
	void controllerClassesShouldBeNamedXController(){
		classes()
				.that().resideInAPackage("..Controller..")
				.should().haveSimpleNameEndingWith("Controller")
				.check(importedClasses);
	}
	@Test
	void controllerClassesShouldBeAnnotatedWithControllerAnnotation(){
		classes()
				.that().resideInAPackage("..Controller..")
				.should().beAnnotatedWith(RestController.class)
				.orShould().beAnnotatedWith(Controller.class)
				.andShould().beAnnotatedWith(RequestMapping.class)
				.check(importedClasses);
	}
	@Test
	void controllerClassesShouldAccessSreviceClasses() {
		classes()
				.that().resideInAPackage("..Controller..")
				.should().dependOnClassesThat().resideInAnyPackage("..Service..")
				.check(importedClasses);
	}
	@Test
	void controllerClassesShouldNotBeAnnotatedWithServiceAndRepositoryAnnotation() {
		noClasses()
				.that().resideInAPackage("..Controller..")
				.should().beAnnotatedWith(Service.class)
				.andShould().beAnnotatedWith(Repository.class)
				.check(importedClasses);
	}
	@Test
	void fieldsShouldNotBeAnnotatedWithAutowired(){
		noFields()
				.should()
				.beAnnotatedWith(Autowired.class)
				.check(importedClasses);
	}
	@Test
	void controllersShouldHaveAnnotatedWithMappingsMethods(){
		methods()
				.that().areDeclaredInClassesThat().resideInAPackage("..Controller..")
				.should().beAnnotatedWith(RequestMapping.class)
				.orShould().beAnnotatedWith(GetMapping.class)
				.orShould().beAnnotatedWith(PostMapping.class)
				.orShould().beAnnotatedWith(PutMapping.class)
				.orShould().beAnnotatedWith(DeleteMapping.class)
				.check(importedClasses);
	}
	@Test
	void modelsShouldHaveAnnotationDocumentAndSchema(){
		classes()
				.that().resideInAPackage("..Model..")
				.should().beAnnotatedWith(Document.class)
				//.andShould().beAnnotatedWith(Schema.class)
				.check(importedClasses);
	}
	@Test
	void modelsShouldNotHavePublicFields(){
		noFields().that().areDeclaredInClassesThat().resideInAPackage("..Model..")
				.should().bePublic()
				.check(importedClasses);
	}
}
