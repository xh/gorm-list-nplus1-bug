package grailsSixZeroZero

class Flight {
    String number
    Location destination
    static belongsTo = [airport: Airport]
    static mapping = {
       destination fetch: 'join'
    }
}