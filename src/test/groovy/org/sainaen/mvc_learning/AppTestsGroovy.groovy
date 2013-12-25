package org.sainaen.mvc_learning

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
class AppTestsGroovy extends Specification {
    @Autowired
    WebApplicationContext wac
    MockMvc mockMvc

    def setup() {
        mockMvc = webAppContextSetup(wac).build()
    }

    def "should greet the world if there's no name passed"() {
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

    def "should send greeting with the name from the request"() {
        when: "request sent"
        def request = mockMvc.perform(get("/").param("name", "Name"))

        then:
        request.andExpect(status().isOk())
        and:
        request.andExpect(model().attribute("message", "Hello, Name!"))
        and:
        request.andExpect(view().name("hello"))
        and:
        request.andExpect(forwardedUrl("/WEB-INF/pages/hello.jsp"))
    }
}
