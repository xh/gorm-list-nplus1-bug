package io.xh.grails

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->
        testData()
    }

    @Transactional
    def testData() {
        for (i in 1..3) {
            def destination = new Location(city: "City $i")
            def flight = new Flight(number: "Flight $i", destination: destination)
            flight.save(flush: true)
            println "Created Flight $i"
        }
    }

    def destroy = {
    }
}
