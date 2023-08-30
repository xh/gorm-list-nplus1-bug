package io.xh.grails

class Flight {

    String number
    Location destination

    static mapping = {
       destination fetch: 'join'
    }
}
