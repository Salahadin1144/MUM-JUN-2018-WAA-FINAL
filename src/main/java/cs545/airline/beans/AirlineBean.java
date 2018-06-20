package cs545.airline.beans;


import cs545.airline.model.Airline;
import cs545.airline.model.Flight;
import cs545.airline.service.AirlineService;
import cs545.airline.service.FlightService;
import org.primefaces.PrimeFaces;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.api.UIData;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
@SessionScoped
public class AirlineBean implements Serializable {

    private long id;
    private String name;
    private String selectedName;
    private Airline selectedAirline = null;
    private List<Flight> selectedAirlineFlights = new ArrayList<>();
    private List<Flight> deletedFlights = new ArrayList<>();
    private Flight selectedFlight;
    private boolean editable;
    private String btnName = "update";
    private DataTable flightDataTable;
    private boolean value;

    @Inject
    private AirlineService airlineService;
    @Inject
    private FlightService flightService;

    public AirlineBean() {
          // ("flightList")
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void addMessage(Flight selectedFlight) {
        if(value)
            this.selectedFlight = selectedFlight;
        String summary = value ? "Selected" + this.selectedFlight.getId() : "Unselected";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
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

    public String getSelectedName() {
        return selectedName;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }

    public Airline getSelectedAirline() {
        return selectedAirline;
    }

    public void setSelectedAirline(Airline selectedAirline) {
        this.selectedAirline = selectedAirline;
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    public void setSelectedFlight(Flight selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    public List<Airline> getAirlines(){
        return airlineService.findAll();
    }

    public List<Flight> getSelectedAirlineFlights() {
        return selectedAirlineFlights;
    }

    public void setSelectedAirlineFlights(List<Flight> selectedAirlineFlights) {
        this.selectedAirlineFlights = selectedAirlineFlights;
    }

    public List<Flight> getDeletedFlights() {
        return deletedFlights;
    }

    public void setDeletedFlights(List<Flight> deletedFlights) {
        this.deletedFlights = deletedFlights;
    }

    public String saveAirline(){
        Airline airline = new Airline();
        airline.setId(this.getId());
        airline.setName(this.getName());
        airlineService.create(airline);
        return "flight";
    }

    public void onRowEdit(RowEditEvent event) {
        this.selectedFlight = (Flight) event.getObject();
        Flight flight = flightService.update(this.selectedFlight);
        FacesMessage msg = null;
        if(flight != null){
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed successfully", "New:" + this.selectedFlight.getId());

        }else{
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell not Changed!", "New:" + this.selectedFlight.getId());
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Flight) event.getObject()).getId()+"");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if(newValue != null && !newValue.equals(oldValue)) {

        }
    }

    public void setFlightToDelete(Flight flight){
        this.selectedFlight = flight;
    }

    public void deleteFlight(){
        flightService.deleteFlight(this.selectedFlight);
        this.value = false;
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        DataTable tabler = (DataTable) view.findComponent(":form:flightList");
        tabler.loadLazyData();
    }

    public void deleteFlight(Flight flight){


        deletedFlights.add(flight);


//        List<UIColumn> coler = tabler.getColumns();
//        //List<UIData> rows;
//        int rowIndex = tabler.getRowIndex();
//        UIData data = (UIData) tabler.getRowData();
//        for(int i=0; i<data.getRows(); i++){
//
//        }

//        String strmsg = ""+rowIndex;
//        for (int i = 0; i < coler.size(); i++) {
//
//            strmsg += "/////////////////\n";
//            strmsg += coler.get(i).getValueExpression("headerText");//.getValue(FacesContext.getCurrentInstance().getELContext())+"\n";
//            strmsg += coler.get(i).getHeaderText()+"\n";
//            strmsg += coler.get(i).isRendered()+"\n";
//            strmsg += coler.get(i).isResizable()+"\n";
//            strmsg += coler.get(i).getWidth()+"\n";
//            strmsg += "/////////////////";
//
//        }
//        strmsg += coler.size();
//        FacesMessage msg = new FacesMessage(strmsg);
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onAirlineChange(){
        this.selectedAirline = airlineService.findByName(selectedName);
        this.selectedAirlineFlights = this.selectedAirline.getFlights();

    }

    public List<String> getAirlineNames(){
        return airlineService.findAll().stream().map(a -> a.getName()).collect(Collectors.toList());
    }

    public List<Airline> getAirlinesByNames(){
        return airlineService.findAll().stream().filter(a -> a.getName().equals(selectedName)).collect(Collectors.toList());
    }

    public void edit(){

    }
}
