package com.gbk;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;
import java.io.StringReader;

import com.mathworks.toolbox.javabuilder.*;
import BeautifulPlot.Plot;


/**
 * Author: jonny.
 */

@Path("matlab")
public class MatlabResource {

    @POST
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public String customGraphResponse(String jsonData) {
        StringReader stringReader = new StringReader(jsonData);
        JsonReader jsonReader = Json.createReader(stringReader);
        try {
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();
            stringReader.close();
            Integer lowerLimit = 1;
            Integer upperLimit = 50;
            try {
                lowerLimit = jsonObject.getInt("lowerLimit");
                upperLimit = jsonObject.getInt("upperLimit");
            } catch(NullPointerException|ClassCastException ex) {
                // Who cares
            }
            return getGraph(lowerLimit, upperLimit);
        } catch (JsonParsingException ex) {
            jsonReader.close();
            stringReader.close();
            return getGraph(1, 50);
        }
    }

    @GET
    @Produces("text/plain")
    public String defaultGraphResponse() {
        StringReader stringReader = new StringReader("dilly");
        JsonReader jsonReader = Json.createReader(stringReader);
        jsonReader.close();
        stringReader.close();
        return getGraph(1,50);
    }

    private String getGraph(Integer n, Integer m) {
        byte[] bytes = {};
        Plot plot = null;
        Object[] output = null;
        MWNumericArray numericImageByteArray;
        MWNumericArray matlabn = new MWNumericArray(n, MWClassID.DOUBLE);
        MWNumericArray matlabm = new MWNumericArray(m, MWClassID.DOUBLE);
        try {
            plot = new Plot();
            output = plot.beaut(1, matlabn, matlabm);
            numericImageByteArray = (MWNumericArray)output[0];
            bytes = numericImageByteArray.getByteData();
        } catch (MWException ex) {
            ex.printStackTrace();
        } finally {
            MWArray.disposeArray(matlabn);
            MWArray.disposeArray(matlabm);
            MWArray.disposeArray(output);
            if (plot != null) {
                plot.dispose();
            }
        }

        return DatatypeConverter.printBase64Binary(bytes);
    }

}