package com.afossey.projects.educational.gists;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.Collections;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class ContractTestsBase {

  private static final String OUTPUT = "build/generated-snippets";

  @Rule
  public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(OUTPUT);

  @Rule
  public TestName testName = new TestName();

  @Autowired
  private GistController gistController;

  @MockBean
  private GistService gistService;

  @MockBean
  private GistSchema gistSchema;

  @Before
  public void setup() {
    GistValue aGist = new GistValue("1", "Foo");
    when(gistService.findGists()).thenReturn(Collections.singletonList(aGist));
    when(gistService.findGistById(anyString())).thenReturn(Optional.of(aGist));
    when(gistService.createGist(any(GistValue.class))).thenReturn(aGist);

    RestAssuredMockMvc.standaloneSetup(
        MockMvcBuilders.standaloneSetup(gistController)
            .apply(documentationConfiguration(this.restDocumentation))
            .alwaysDo(document((getClass().getSimpleName() + "_" + testName.getMethodName()),
                resource(testName.getMethodName())))
    );
  }
}
