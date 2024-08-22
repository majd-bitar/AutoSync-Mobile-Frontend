package com.majd.obd.reader.obdCommand.engine;

import com.majd.obd.reader.enums.AvailableCommandNames;
import com.majd.obd.reader.obdCommand.fuel.PercentageObdCommand;

/**
 * Read the throttle position in percentage.
 *
 */
public class ThrottlePositionCommand extends PercentageObdCommand {

    /**
     * Default ctor.
     */
    public ThrottlePositionCommand() {
        super("01 11");
    }

    /**
     * Copy ctor.
     *
     */
    public ThrottlePositionCommand(ThrottlePositionCommand other) {
        super(other);
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return AvailableCommandNames.THROTTLE_POS.getValue();
    }

}
