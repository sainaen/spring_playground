package org.sainaen.mvc_learning

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
class AppTestsGroovy extends Specification {
    @Autowired
    WebApplicationContext wac
    MockMvc mockMvc

    def setup() {
        mockMvc = webAppContextSetup(wac).build()
    }

    def "should greet the world"() {
        when: "request sent"
        def result = mockMvc.perform(get("/")).andReturn()

        and: "there's result from the controller"
        def modelAndView = result.getModelAndView()
        def response = result.getResponse()

        then: "controller should populate model with default message"
        modelAndView.model.get("message") == "Hello, World!"

        and: "controller should select right view"
        modelAndView.viewName == "hello"

        and: "response should have status 200 OK"
        response.getStatus() == HttpStatus.OK.value()
    }
}
