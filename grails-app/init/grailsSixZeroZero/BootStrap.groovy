package grailsSixZeroZero

import grails.gorm.transactions.Transactional

class BootStrap {

    def init = { servletContext ->
        testData()
    }

    @Transactional
    def testData() {
        for (i in 1..3) {
            def flights = []
            for(j in 1..7) {
                flights << new Flight(number: "Flight $i-$j", destination: new Location(city: "City $i-$j", country: "Country $i-$j"))
            }
            def airport = new Airport(name: "Airport $i", flights: flights)
            airport.save(flush: true)
        }
    }

    def destroy = {
    }
}
