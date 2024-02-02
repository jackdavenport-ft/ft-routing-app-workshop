/****************************************************************
* Copyright (c) FutureTech Australia 2024
* All rights reserved, this code is available for educational
* purposes. Do not copy or redistribute.
* @author Jack Davenport
****************************************************************/
package com.ft.routing.ui;

import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.ft.routing.client.RouteTable;
import com.ft.routing.server.Util;

public class RoutingTableModel implements TableModel {

    private static final String[] HEADER_LABELS = { "Username", "IP Address" };

    private final Set<TableModelListener> modelListeners = new HashSet<>();

    @Override
    public int getRowCount() {
        return RouteTable.getRouteCount();
    }

    @Override
    public int getColumnCount() {
        return HEADER_LABELS.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return HEADER_LABELS[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Entry<String, String> entry = RouteTable.getRouteEntry(rowIndex);
        return columnIndex == 0 ? entry.getKey() : entry.getValue();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // change value in route table
        if(aValue instanceof String) {
            String value = ((String)aValue).trim();
            if(value.isEmpty()) return;
            Entry<String,String> entry = RouteTable.getRouteEntry(rowIndex);
            String oldUsername = entry.getKey();
            String oldAddress = entry.getValue();
            if(columnIndex == 0) {
                // update username
                RouteTable.removeAddress(oldUsername);
                RouteTable.setAddress(value, oldAddress);
            } else {
                // validate address fist
                if(!Util.isValidIpFormat(value)) return;
                // update address
                RouteTable.setAddress(oldUsername, value);
            }
        }
        // notify table listeners of change
        for(TableModelListener l : this.modelListeners) {
            l.tableChanged(new TableModelEvent(this, rowIndex, rowIndex, columnIndex));
        }   
    }

    public void refreshTable() {
        for(TableModelListener l : this.modelListeners) {
            l.tableChanged(new TableModelEvent(this, 0, RouteTable.getRouteCount(), TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        modelListeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        modelListeners.remove(l);
    }
    
}
