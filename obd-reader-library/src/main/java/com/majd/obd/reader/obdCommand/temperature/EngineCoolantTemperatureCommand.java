package com.majd.obd.reader.obdCommand.temperature;

import com.majd.obd.reader.enums.AvailableCommandNames;

/**
 * Engine Coolant Temperature.
 *
 */
public class EngineCoolantTemperatureCommand extends TemperatureCommand {

    /**
     * <p>Constructor for EngineCoolantTemperatureCommand.</p>
     */
    public EngineCoolantTemperatureCommand() {
        super("01 05");
    }

    /**
     * <p>Constructor for EngineCoolantTemperatureCommand.</p>
     *
     */
    public EngineCoolantTemperatureCommand(TemperatureCommand other) {
        super(other);
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return AvailableCommandNames.ENGINE_COOLANT_TEMP.getValue();
    }

}
