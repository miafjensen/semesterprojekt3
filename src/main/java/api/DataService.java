package api;

import controller.ImportController;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("data")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
public class DataService {
    private ArrayList<Integer> dataArrayList = new ArrayList<>();
    private String[] EKGdata;
    private String input = "";
    private String[] splitData;
    //ArrayList<SensorObserver> observers = new ArrayList<>();

    @POST
    public String postEKGData(String EKGdata) {

        //System.out.println(EKGdata);

/*
        String[] split = EKGdata.split(",");

        for (int i = 0; i < split.length; i++) {



            System.out.println(split[i]);
            // System.out.println("Done");
        } */
        return EKGdata;

    }
    /*
    public String[] getSplitData() {

        String material = postEKGData();

        String[] splittet = material.split(",");
        //bruges til at undgå whitespaces.
        //https://stackoverflow.com/questions/13750716/what-does-regular-expression-s-s-do


        splitData = splittet;

        return splitData;
    } */


    public ArrayList<Integer> getDataArrayList() {
        for (String string : EKGdata ) {
            //kører det splittede data igennem
            dataArrayList.add(Integer.parseInt(string));
            //konverterer indhold i array til int, og tilføjes til arrayList plads for plads
        }
        return dataArrayList;
    }
}




