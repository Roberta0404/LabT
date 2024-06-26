package GUI;

import DTO.Strumento;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class ToolsTableModel extends AbstractTableModel {

    private List<Strumento> toolsList;

    private final String[] columnsName = {"Codice Strumento", "Caratteristiche", "Descrizione", "Tempo Max Uso", "Cod. Postazione", "Sede"};

    public ToolsTableModel(List<Strumento> tools) {

        toolsList = tools;

    }


    @Override
    public String getColumnName(int column) {

        return columnsName[column];

    }

    @Override
    public int getRowCount() {

        if (toolsList != null) {

            return toolsList.size();

        } else {

            return 22;
        }

    }

    @Override
    public int getColumnCount() {
        return 6;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {


        if (toolsList != null) {

            Strumento strumento = toolsList.get(rowIndex);

            return switch (columnIndex) {

                case 0 -> strumento.getCodStrumento();
                case 1 -> strumento.getCaratteristiche_tecniche();
                case 2 -> strumento.getDescrizione();
                case 3 -> {

                    SimpleDateFormat sdfTime4 = new SimpleDateFormat("HH:mm");
                    yield sdfTime4.format(strumento.getTempoMaxUso());

                }

                case 4 -> strumento.getCodPostazione_fk().getCodPostazione();
                case 5 -> strumento.getCodSede_fk().getNome();
                default -> null;

            };

        } else {

            return null;

        }

    }

    public void setData(List<Strumento> tools) {

        toolsList = tools;

        fireTableDataChanged(); // Notifica alla tabella che i dati sono stati cambiati

    }

    public Strumento getToolAtRow(int rowIndex) {

        if (toolsList != null && rowIndex >= 0 && rowIndex < toolsList.size()) {
            return toolsList.get(rowIndex);

        }

        return null;

    }

}
