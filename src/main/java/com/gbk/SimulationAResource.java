package com.gbk;

import Simulations.SimulationA;
import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import javax.ws.rs.*;
import javax.xml.bind.DatatypeConverter;

@Path("SimulationA")
public class SimulationAResource {

    @GET
    @Produces("image/png")
    public String respond(@DefaultValue("2000000") @QueryParam("initialCapital") int initialCapital, @DefaultValue("1500") @QueryParam("flatFee") int flatFee, @DefaultValue("0.5") @QueryParam("stampDutyRate") float stampDutyRate, @DefaultValue("0.5") @QueryParam("compoundInterestRate") float compoundInterestRate) {
        return getGraph(initialCapital, flatFee, stampDutyRate/100, compoundInterestRate/100);
    }

    private String getGraph(Integer initialCapital, Integer flatFee, Float stampDutyRate, Float compoundInterestRate) {
        byte[] bytes = {};
        SimulationA simulation = null;
        Object[] output = null;
        MWNumericArray numericImageByteArray;
        MWNumericArray matlabInitialCapital = new MWNumericArray(initialCapital, MWClassID.DOUBLE);
        MWNumericArray matlabFlatFee = new MWNumericArray(flatFee, MWClassID.DOUBLE);
        MWNumericArray matlabStampDutyRate = new MWNumericArray(stampDutyRate, MWClassID.DOUBLE);
        MWNumericArray matlabCompoundInterestRate = new MWNumericArray(compoundInterestRate, MWClassID.DOUBLE);
        try {
            simulation = new SimulationA();
            output = simulation.plotSimulationResults(1, matlabInitialCapital, matlabFlatFee, matlabStampDutyRate, matlabCompoundInterestRate);
            numericImageByteArray = (MWNumericArray)output[0];
            bytes = numericImageByteArray.getByteData();
        } catch (MWException ex) {
            ex.printStackTrace();
        } finally {
            MWArray.disposeArray(matlabInitialCapital);
            MWArray.disposeArray(matlabFlatFee);
            MWArray.disposeArray(matlabStampDutyRate);
            MWArray.disposeArray(matlabFlatFee);
            MWArray.disposeArray(matlabCompoundInterestRate);
            if (simulation != null) {
                simulation.dispose();
            }
        }

        return DatatypeConverter.printBase64Binary(bytes);
    }

}
