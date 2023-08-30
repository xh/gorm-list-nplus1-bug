package grailsSixZeroZero

import grails.converters.JSON
import grails.gorm.transactions.ReadOnly

class TesterController {

    @ReadOnly
    def testa0() {
        def results = []
        println "\n\nFind:"
        def airport = Airport.findByName("Airport 1")
        println "\n\nLoop:"
        for (flight in airport.flights) {
            results << flight.destination.city
        }
        println "\n\nResults:"
        renderJSON([success: true, results: results])
    }

    @ReadOnly
    def testa1() {
        def results = []
        println "\n\nFind:"
        def airports = Airport.findAll()
        println "\n\nLoop:"
        for(airport in airports) {
            for (flight in airport.flights) {
                results << flight.destination.city
            }
        }
        println "\n\nResults:"
        renderJSON([success: true, results: results])
    }

    @ReadOnly
    def testa2() {
        def results = []
        println "\n\nFind:"
        def airports = Airport.findAll(fetch: [flights: 'join'])
        println "\n\nLoop:"
        for(airport in airports) {
            for (flight in airport.flights) {
                results << flight.destination.city
            }
        }
        println "\n\nResults:"
        renderJSON([success: true, results: results])
    }

    @ReadOnly
    def testa3() {
        def results = []
        println "\n\nFind:"
        def airports = Airport.findAll("from Airport as airport " +
                "left join fetch airport.flights flights " +
                "left join fetch flights.destination")
        println "\n\nLoop:"
        for(airport in airports) {
            for (flight in airport.flights) {
                results << flight.destination.city
            }
        }
        println "\n\nResults:"
        renderJSON([success: true, results: results])
    }

    @ReadOnly
    def testa4() {
        def results = []
        println "\n\nFind:"
        Location.findAll()
        Flight.findAll()
        def airports = Airport.findAll()
        println "\n\nLoop:"
        for(airport in airports) {
            for (flight in airport.flights) {
                results << flight.destination.city
            }
        }
        println "\n\nResults:"
        renderJSON([success: true, results: results])
    }

    @ReadOnly
    def testb0() {
        def results = []
        println "\n\nFind:"
        def flight = Flight.findByNumber('Flight 1-1')
        println "\n\nLoop:"
        results << flight.destination.city
        println "\n\nResults:"
        renderJSON([success: true, results: results])
    }

    @ReadOnly
    def testb1() {
        def results = []
        println "\n\nFind:"
        def flights = Flight.findAll()
        println "\n\nLoop:"
            for (flight in flights) {
                results << flight.destination.city
            }
        println "\n\nResults:"
        renderJSON([success: true, results: results])
    }

    @ReadOnly
    def testb2() {
        def results = []
        println "\n\nFind:"
        def flights = Flight.findAll(fetch: [destination: 'join'])
        println "\n\nLoop:"
        for (flight in flights) {
            results << flight.destination.city
        }
        println "\n\nResults:"
        renderJSON([success: true, results: results])
    }


    @ReadOnly
    def testb3() {
        def results = []
        println "\n\nFind:"
        def flights = Flight.findAll("from Flight as flight left join fetch flight.destination")
        println "\n\nLoop:"
        for (flight in flights) {
            results << flight.destination.city
        }
        println "\n\nResults:"
        renderJSON([success: true, results: results])
    }

    @ReadOnly
    def testb4() {
        def results = []
        println "\n\nFind:"
        Location.findAll()
        def flights = Flight.findAll()
        println "\n\nLoop:"
        for (flight in flights) {
            results << flight.destination.city
        }
        println "\n\nResults:"
        renderJSON([success: true, results: results])
    }

    protected void renderJSON(Object o){
        response.setContentType('application/json; charset=UTF-8')
        render(o as JSON)
    }
}
