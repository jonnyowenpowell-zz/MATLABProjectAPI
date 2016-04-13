package com.gbk;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.mathworks.toolbox.javabuilder.*;
import BeautifulPlot.Plot;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Author: jonny.
 */

@Path("matlab")
public class MatlabResource {

    @Path("{n: [0-9]*}_{m: [0-9]*}")
    @GET
    @Produces({"image/png"})
    public Response getGraph(@PathParam("n") String fromInteger, @PathParam("m") String toInteger) {

        Integer n = Integer.parseInt(fromInteger);
        Integer m = Integer.parseInt(toInteger);

        final byte[] image = getPlotByteArray(n,m);

        return Response.ok().entity(new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                output.write(image);
                output.flush();
            }
        }).build();
    }

    @Path("{n: [0-9]*}")
    @GET
    @Produces({"image/png"})
    public Response getGraph(@PathParam("n") String toInteger) {
        return getGraph("1",toInteger);
    }

    @GET
    @Produces({"image/png"})
    public Response getGraph() {
        return getGraph("1","50");
    }

    private byte[] getPlotByteArray(Integer n, Integer m) {
        Plot plot = null;
        Object[] output = null;
        MWNumericArray numericImageByteArray;
        MWNumericArray matlabn = new MWNumericArray(n, MWClassID.DOUBLE);
        MWNumericArray matlabm = new MWNumericArray(m, MWClassID.DOUBLE);

        try {
            plot = new Plot();
            output = plot.beaut(1, matlabn, matlabm);
            numericImageByteArray = (MWNumericArray)output[0];
            return numericImageByteArray.getByteData();
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

        return new byte[]{};
    }

}