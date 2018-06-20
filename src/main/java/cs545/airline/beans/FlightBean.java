package cs545.airline.beans;

import cs545.airline.model.Airline;
import cs545.airline.model.Airplane;
import cs545.airline.model.Airport;
import cs545.airline.model.Flight;
import cs545.airline.service.AirlineService;
import cs545.airline.service.AirplaneService;
import cs545.airline.service.AirportService;
import cs545.airline.service.FlightService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class FlightBean implements Serializable {
    @Inject
    private FlightService flightService;
    @Inject
    private AirlineService airlineService;
    @Inject
    private AirplaneService airplaneService;
    @Inject
    private AirportService airportService;

    private long id;
    private String flightNumber;
    private Date departureDate;
    private Date departureTime;
    private Date arrivalDate;
    private Date arrivalTime;
    private Airline airline;
    private String airlineName;

    private Airport origin;
    private String originName;

    private Airport destination;
    private String destinationName;

    private Airplane airplane;

    private String airplaneModel;

    public long getId() {
        return id;

    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getAirplaneModel() {
        return airplaneModel;
    }

    public void setAirplaneModel(String airplaneModel) {
        this.airplaneModel = airplaneModel;
    }

    public List<String> getAllAirlineNames(){
        return airlineService.findAll().stream().map(a -> a.getName()).collect(Collectors.toList());
    }

    public List<String> getAllAirplaneSerialNumbers(){
        return airplaneService.findAll().stream().map(a -> a.getSerialnr()).collect(Collectors.toList());
    }

    public List<String> getAllAirportCodes(){
        return airportService.findAll().stream().map(a -> a.getAirportcode()).collect(Collectors.toList());
    }

    public String saveFlight(){
        Flight flight = new Flight();
        flight.setFlightnr(this.flightNumber);
        flight.setDepartureDate(this.departureDate.toString());
        flight.setDepartureTime(this.departureTime.toString());
        flight.setArrivalDate(this.arrivalDate.toString());
        flight.setArrivalTime(this.arrivalTime.toString());
        flight.setAirline(airlineService.findByName(this.airlineName));
        flight.setOrigin(airportService.findByCode(this.originName));
        flight.setDestination(airportService.findByCode(this.destinationName));
        flight.setAirplane(airplaneService.findBySrlnr(this.airplaneModel));
        flightService.create(flight);
        return "flight?faces-redirect=true";
    }
}
