package com.gbk;

import javax.ws.rs.*;
import javax.xml.bind.DatatypeConverter;

import com.mathworks.toolbox.javabuilder.*;
import BeautifulPlot.Plot;


/**
 * Author: jonny.
 */

@Path("matlab")
public class MatlabResource {

    @Path("{n: [0-9]*}_{m: [0-9]*}")
    @GET
    @Produces("text/plain")
    public String customGraphResponse(@PathParam("n") String fromInteger, @PathParam("m") String toInteger) {
        Integer n = Integer.parseInt(fromInteger);
        Integer m = Integer.parseInt(toInteger);
        return getGraph(n, m);
    }

    @Path("{n: [0-9]*}")
    @GET
    @Produces("text/plain")
    public String partialCustomGraphResponse(@PathParam("n") String toInteger) {
        Integer n = Integer.parseInt(toInteger);
        return getGraph(1, n);
    }

    @GET
    @Produces("text/plain")
    public String getGraph() {
        return getGraph(1, 50);
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