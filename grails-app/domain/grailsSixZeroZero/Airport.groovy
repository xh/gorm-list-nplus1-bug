package grailsSixZeroZero

class Airport {
    String name
    static hasMany = [flights: Flight]
    static mapping = {
        flights fetch: 'join'
    }
}