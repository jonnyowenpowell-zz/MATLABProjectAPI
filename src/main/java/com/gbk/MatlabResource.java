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

    @GET
    @Produces("text/plain")
    public String respond(@DefaultValue("1") @QueryParam("from") int from, @DefaultValue("50") @QueryParam("from") int to) {
        return getGraph(from, to);
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