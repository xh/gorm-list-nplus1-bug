package io.xh.grails

import grails.converters.JSON
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TestController {

    protected static final Logger log = LoggerFactory.getLogger(TestController.class);

    /** Does not trigger n+1 - single query to fetch flight and location (as expected). */
    def findSingle() {
        Flight flight
        withLog('Find one flight') {
            flight = Flight.findByNumber('Flight 1')
        }
        renderWithCities([flight])
    }

    /** Triggers n+1, despite destination fetch: 'join' - no sign of join in query. */
    def findAll() {
        Collection<Flight> flights
        withLog('Find all flights') {
            flights = Flight.findAll()
        }
        renderWithCities(flights)
    }

    /** Triggers n+1, adds "useless" inner join on location but does not select any properties from it. */
    def findAllWithFetchArg() {
        Collection<Flight> flights
        withLog('Find all flights with fetch/join query arg') {
            flights = Flight.findAll(fetch: [destination: 'join'])
        }
        renderWithCities(flights)
    }


    /** Does not trigger n+1 - single query to get all data (but would need to test with additional joins or collections - could dupe results) */
    def findAllWithHql() {
        Collection<Flight> flights
        withLog('Find all flights with SQL') {
            flights = Flight.findAll("from Flight as flight left join fetch flight.destination")
        }
        renderWithCities(flights)
    }

    /** Does not trigger n+1 - one query to get all locations, another to get flights, no n+1 location queries */
    def findAllWithLocationPrefetch() {
        withLog('Find all locations, before flights') {
            Location.findAll()
        }
        findAll()
    }


    //------------------
    // Implementation
    //------------------
    private renderWithCities(Collection<Flight> flights) {
        withLog('Render with destination.city') {
            renderJSON(
                    flights: flights.collect { f ->
                        [number: f.number, city: f.destination.city]
                    }
            )
        }
    }

    private void withLog(String msg, Closure c) {
        log.info('')
        log.info('--------------------------------------------------')
        log.info("START $msg >>")
        c.call()
        log.info("<< END $msg")
        log.info('--------------------------------------------------')
    }

    private void renderJSON(Object o) {
        response.setContentType('application/json; charset=UTF-8')
        render(o as JSON)
    }
}
