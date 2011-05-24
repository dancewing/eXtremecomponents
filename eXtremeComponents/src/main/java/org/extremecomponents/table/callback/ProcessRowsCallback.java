package org.extremecomponents.table.callback;

import org.extremecomponents.table.core.TableModel;

import java.util.Collection;

/**
 * Created by jeff
 */
public class ProcessRowsCallback implements RetrieveRowsCallback {
    @Override
    public Collection retrieveRows(TableModel model, Collection rows) throws Exception {
        return rows;
    }
}
