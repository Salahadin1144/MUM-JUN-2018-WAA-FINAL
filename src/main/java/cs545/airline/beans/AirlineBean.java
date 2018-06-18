package cs545.airline.beans;


import cs545.airline.model.Airline;
import cs545.airline.service.AirlineService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class AirlineBean implements Serializable {

    private long id;
    private String name;
    private List<Airline> airlineList;

    @Inject
    private AirlineService airlineService;

    public AirlineBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Airline> getAirlineList() {
        return airlineList;
    }

    public void setAirlineList(List<Airline> airlineList) {
        this.airlineList = airlineList;
    }

    public String saveAirline(){
        Airline airline = new Airline();
        airline.setId(this.getId());
        airline.setName(this.getName());
        //airline.addFlight(null);
        FacesMessage msg = new FacesMessage("BEAN>>>>>>> "+airline.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        //AirlineDao airlineDao = new AirlineDao();
        airlineService.create(airline);
        return "AirlinesList";
    }

    public List<Airline> getAirlines(){
        return airlineService.findAll();
    }
}
