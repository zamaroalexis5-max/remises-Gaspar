package Formularios;

import Metodos_sql.ConexionBD;
import Metodos_sql.GenerarCodigos;
import Metodos_sql.MetodosSql;
import Metodos_sql.ViajeCerrado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author José Pérez
 */
public class Panel_Viajes extends javax.swing.JPanel {

    /**
     * Creates new form Panel_Viajes
     */
    public Panel_Viajes() {
        initComponents();

        FechaActual();
        run();

        mostrarDatos();
        ViajeCerrado1();

        blockcajas();
    }

    MetodosSql Met = new MetodosSql();
    ConexionBD cc = new ConexionBD();
    Connection con = cc.conectar();

    public void blockcajas() {

        TxtCelTelch.setVisible(false);
        TxtCodch.setVisible(false);
        TxtDueñoch.setVisible(false);
        TxtMarcach.setVisible(true);
        TxtNombrech.setVisible(true);
        TxtDnich.setVisible(false);
        TxtModeloch.setVisible(false);
        TxtApellidoC.setVisible(true);
        TxtDniC.setVisible(true);
        TxtCodC.setVisible(false);
        TxtDominioch.setVisible(true);
        TxtFecha.setVisible(true);
        TxtGasto.setVisible(false);
        TxtCosto.setVisible(false);
        TxtTotal.setVisible(false);
        TxtComentario.setVisible(true);
        TxtEntrecalleC.setVisible(false);

        TxtApellidoch.setEditable(false);
        TxtMarcach.setEditable(false);
        TxtNombreC.setEditable(false);
        TxtDireccionC.setEditable(false);
        TxtEntrecalleC.setEditable(false);
        TxtDominioch.setEditable(false);
        TxtApellidoC.setEditable(false);
        TxtNombrech.setEditable(false);
        TxtCod_v.setEditable(false);
        TxtDniC.setEditable(false);

    }

    public void limpiarCajas() {

        TxtNombreC.setText("");
        TxtApellidoC.setText("");
        TxtDniC.setText("");
        TxtDireccionC.setText("");
        TxtNombrech.setText("");
        TxtApellidoch.setText("");
        TxtDnich.setText("");
        TxtMarcach.setText("");
        TxtDominioch.setText("");
        TxtOrigen.setText("");
        TxtDestino.setText("");

        TxtComentario.setText("");

    }

    public void LimpiarCostos() {
        TxtGasto.setText("0");
        TxtCosto.setText("0");
        TxtTotal.setText("0");

    }

    public void ViajeCerrado1() {

        JTViajes.getColumnModel().getColumn(16).setCellRenderer(new ViajeCerrado());

    }

    public void FiltrarFecha(String valor) {
        //CON ESTE METODO PODEMOS FLITRAR BUSQUEDAS ESPECIFICAS
        String[] titulos = {"Cod. Viaje", "Nom. Cli", "Ape. Cli", "Dni Cli", "Dir. Cli", "Nom. Chofer", "Ape. Chofer", "Dni Chofer", "Marca", "Dominio", "Origen", "Destino", "Fecha", "Hora", "Costo $", "Gasto $", "Total $", "comentario"};
        String[] registros = new String[18];

        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String SQL = "SELECT * FROM labarrera.viajes WHERE CONCAT(Cod_viajes,nom_cli,ape_cli,dni_cli,dire_cli,nom_chof,dni_chof,marca_chof,dom_chof,origen,destino,fecha,hora,costo,gasto,total,comentario) LIKE '%" + valor + "%'";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {

                registros[0] = rs.getString("cod_viajes");
                registros[1] = rs.getString("nom_cli");
                registros[2] = rs.getString("ape_cli");
                registros[3] = rs.getString("dni_cli");
                registros[4] = rs.getString("dire_cli");
                registros[5] = rs.getString("nom_chof");
                registros[6] = rs.getString("ape_chof");
                registros[7] = rs.getString("dni_chof");
                registros[8] = rs.getString("marca_chof");
                registros[9] = rs.getString("dom_chof");
                registros[10] = rs.getString("origen");
                registros[11] = rs.getString("destino");
                registros[12] = rs.getString("fecha");
                registros[13] = rs.getString("hora");
                registros[14] = rs.getString("costo");
                registros[15] = rs.getString("gasto");
                registros[16] = rs.getString("total");
                registros[17] = rs.getString("comentario");

//                
                modelo.addRow(registros);

            }
            JTViajes.setModel(modelo);
            ViajeCerrado1();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al monstrar el mensaje" + e);
        }

    }

    public void FechaActual() { //Metodo para mostara la fecha 

        LocalDateTime ahora = LocalDateTime.now();
        int año = ahora.getYear();
        int mes = ahora.getMonthValue();
        int dia = ahora.getDayOfMonth();

        TxtFecha.setText(dia + "/" + mes + "/" + año);
    }

    public void run() { //Metodo con hilos para actualizar la hora
        DateTimeFormatter formateador;
        formateador = DateTimeFormatter.ofPattern("HH:mm:ss");
        Runnable runnable;
        runnable = () -> {
            while (true) {
                try {
                    Thread.sleep(500);
                    TxtHora.setText(formateador.format(LocalDateTime.now()));
                } catch (InterruptedException e) {
                }
            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();

    }

    public void mostrarDatos() {
        //CON ESTE METODO MOSTRAMOS EL CONTENIDO DE LA TABLA
        String[] titulos = {"Cod. Viaje", "Nom. Cli", "Ape. Cli", "Dni Cli", "Dir. Cli", "Nom. Chofer", "Ape. Chofer", "Dni Chofer", "Marca", "Dominio", "Origen", "Destino", "Fecha", "Hora", "Costo $", "Gasto $", "Total $", "comentario"};
        String[] registros = new String[18];

        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        String SQL = "SELECT * FROM labarrera.viajes";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {

                registros[0] = rs.getString("cod_viajes");
                registros[1] = rs.getString("nom_cli");
                registros[2] = rs.getString("ape_cli");
                registros[3] = rs.getString("dni_cli");
                registros[4] = rs.getString("dire_cli");
                registros[5] = rs.getString("nom_chof");
                registros[6] = rs.getString("ape_chof");
                registros[7] = rs.getString("dni_chof");
                registros[8] = rs.getString("marca_chof");
                registros[9] = rs.getString("dom_chof");
                registros[10] = rs.getString("origen");
                registros[11] = rs.getString("destino");
                registros[12] = rs.getString("fecha");
                registros[13] = rs.getString("hora");
                registros[14] = rs.getString("costo");
                registros[15] = rs.getString("gasto");
                registros[16] = rs.getString("total");
                registros[17] = rs.getString("comentario");

                modelo.addRow(registros);

            }
            JTViajes.setModel(modelo);
            ViajeCerrado1();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al monstrar el mensaje" + e);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        JPPrimeraCapa = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        JPDATOS = new javax.swing.JPanel();
        TxtApellidoch = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        TxtMarcach = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        TxtDestino = new javax.swing.JTextField();
        TxtOrigen = new javax.swing.JTextField();
        TxtNombreC = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        LblHora = new javax.swing.JLabel();
        LblFecha = new javax.swing.JLabel();
        TxtCodch = new javax.swing.JTextField();
        TxtDnich = new javax.swing.JTextField();
        TxtNombrech = new javax.swing.JTextField();
        TxtCelTelch = new javax.swing.JTextField();
        TxtModeloch = new javax.swing.JTextField();
        TxtDueñoch = new javax.swing.JTextField();
        TxtCodC = new javax.swing.JTextField();
        TxtDireccionC = new javax.swing.JTextField();
        TxtEntrecalleC = new javax.swing.JTextField();
        TxtDniC = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        TxtApellidoC = new javax.swing.JTextField();
        TxtDominioch = new javax.swing.JTextField();
        TxtCod_v = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        TxtFecha = new javax.swing.JLabel();
        TxtHora = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TxtGasto = new javax.swing.JTextField();
        TxtCosto = new javax.swing.JTextField();
        TxtTotal = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        TxtComentario = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTViajes = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        TxtBuscar = new javax.swing.JTextField();
        BtnBorrar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        BtnCerrarViaje = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        jEditorPane2 = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        JPPrimeraCapa.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 0, 0));
        jLabel1.setFont(new java.awt.Font("OCR A Extended", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 51));
        jLabel1.setText("Cargar todos los datos :");

        JPDATOS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        TxtApellidoch.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N

        jLabel34.setText("Chofer:");

        jLabel35.setText("Marca:");

        TxtMarcach.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N

        jButton7.setText("Agregar Viaje");
        jButton7.setActionCommand("Agregar \n  Viaje");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        TxtDestino.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N

        TxtOrigen.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N

        TxtNombreC.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N
        TxtNombreC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNombreCActionPerformed(evt);
            }
        });

        jButton1.setText("Agregar cliente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel17.setText("Cliente: ");

        jLabel32.setText("Origen: ");

        jLabel33.setText("Comentario:");

        TxtNombrech.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N

        TxtDireccionC.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N
        TxtDireccionC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtDireccionCActionPerformed(evt);
            }
        });

        TxtEntrecalleC.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N
        TxtEntrecalleC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtEntrecalleCActionPerformed(evt);
            }
        });

        jLabel18.setText("DNI:");

        TxtApellidoC.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N
        TxtApellidoC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtApellidoCActionPerformed(evt);
            }
        });

        TxtDominioch.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N

        TxtCod_v.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N

        jButton3.setText("Nuevo Viaje");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Agregar Chofer");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("OCR A Extended", 1, 12)); // NOI18N
        jLabel2.setText("Fecha:");

        jLabel3.setFont(new java.awt.Font("OCR A Extended", 1, 12)); // NOI18N
        jLabel3.setText("Hora:");

        TxtGasto.setText("0");
        TxtGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtGastoActionPerformed(evt);
            }
        });

        TxtCosto.setText("0");

        TxtTotal.setText("0");

        jLabel19.setText("Direccion: ");

        jLabel20.setText("Dominio:");

        jLabel36.setText("Destino: ");

        jButton2.setText("ACTUALIZAR LOS REGISTROS Y MODIFICACIONES");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        TxtComentario.setColumns(20);
        TxtComentario.setRows(5);
        jScrollPane1.setViewportView(TxtComentario);

        jScrollPane4.setViewportView(jScrollPane1);

        javax.swing.GroupLayout JPDATOSLayout = new javax.swing.GroupLayout(JPDATOS);
        JPDATOS.setLayout(JPDATOSLayout);
        JPDATOSLayout.setHorizontalGroup(
            JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPDATOSLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(TxtNombreC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TxtNombrech, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPDATOSLayout.createSequentialGroup()
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPDATOSLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(LblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(236, 236, 236))
                            .addGroup(JPDATOSLayout.createSequentialGroup()
                                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TxtApellidoC, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtApellidoch, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4)
                                    .addGroup(JPDATOSLayout.createSequentialGroup()
                                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JPDATOSLayout.createSequentialGroup()
                                                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(JPDATOSLayout.createSequentialGroup()
                                                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(61, 61, 61))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPDATOSLayout.createSequentialGroup()
                                                        .addComponent(TxtDireccionC, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(TxtDniC)
                                                    .addGroup(JPDATOSLayout.createSequentialGroup()
                                                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGap(0, 66, Short.MAX_VALUE))))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JPDATOSLayout.createSequentialGroup()
                                                .addComponent(TxtMarcach, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(JPDATOSLayout.createSequentialGroup()
                                                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(0, 0, Short.MAX_VALUE))
                                                    .addComponent(TxtDominioch))))
                                        .addGap(12, 12, 12)
                                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(31, 31, 31)))))
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPDATOSLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(JPDATOSLayout.createSequentialGroup()
                                        .addComponent(TxtEntrecalleC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(JPDATOSLayout.createSequentialGroup()
                                                .addComponent(TxtDnich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(TxtCodch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(JPDATOSLayout.createSequentialGroup()
                                                .addComponent(TxtCodC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(TxtDueñoch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(TxtModeloch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(JPDATOSLayout.createSequentialGroup()
                                            .addComponent(TxtTotal)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(TxtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(TxtGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(TxtCelTelch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPDATOSLayout.createSequentialGroup()
                                .addComponent(TxtCod_v, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33))))
                    .addGroup(JPDATOSLayout.createSequentialGroup()
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(JPDATOSLayout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );
        JPDATOSLayout.setVerticalGroup(
            JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPDATOSLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPDATOSLayout.createSequentialGroup()
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPDATOSLayout.createSequentialGroup()
                                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton1)
                                    .addComponent(TxtDniC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtCod_v, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(JPDATOSLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtNombreC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtApellidoC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtDireccionC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPDATOSLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel20)
                                .addGap(4, 4, 4)
                                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(TxtApellidoch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtNombrech)
                                    .addComponent(TxtDominioch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtMarcach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton4)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPDATOSLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel34))
                                .addGap(37, 37, 37)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPDATOSLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JPDATOSLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPDATOSLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addComponent(LblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPDATOSLayout.createSequentialGroup()
                                .addGap(121, 121, 121)
                                .addComponent(LblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, JPDATOSLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addGap(23, 23, 23))))
                    .addGroup(JPDATOSLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtCelTelch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtCodC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtDueñoch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtModeloch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtEntrecalleC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(JPDATOSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtCodch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtDnich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        JTViajes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        JTViajes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTViajesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(JTViajes);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel37.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel37.setText("BUSQUEDA:");

        TxtBuscar.setFont(new java.awt.Font("OCR A Extended", 2, 14)); // NOI18N
        TxtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtBuscarKeyReleased(evt);
            }
        });

        BtnBorrar.setText("QUITAR FILTROS");
        BtnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBorrarActionPerformed(evt);
            }
        });

        jButton5.setText("VER VIAJE");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        BtnCerrarViaje.setText("CERRAR VIAJE");
        BtnCerrarViaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCerrarViajeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BtnCerrarViaje, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton5)
                        .addComponent(BtnCerrarViaje))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel37)
                        .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BtnBorrar)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jEditorPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Notas editables:"));
        jScrollPane3.setViewportView(jEditorPane2);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Panel_Viajes.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 326, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout JPPrimeraCapaLayout = new javax.swing.GroupLayout(JPPrimeraCapa);
        JPPrimeraCapa.setLayout(JPPrimeraCapaLayout);
        JPPrimeraCapaLayout.setHorizontalGroup(
            JPPrimeraCapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPPrimeraCapaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPPrimeraCapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPPrimeraCapaLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(JPPrimeraCapaLayout.createSequentialGroup()
                        .addGroup(JPPrimeraCapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(JPPrimeraCapaLayout.createSequentialGroup()
                                .addComponent(JPDATOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2)))
        );
        JPPrimeraCapaLayout.setVerticalGroup(
            JPPrimeraCapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPPrimeraCapaLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(JPPrimeraCapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPPrimeraCapaLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(JPPrimeraCapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JPDATOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 829, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(JPPrimeraCapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(JPPrimeraCapa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ViajesClientes ventana = new ViajesClientes();
        ventana.setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed
    public void LimpiarFiltro() {

        TxtBuscar.setText("");

    }
    private void BtnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBorrarActionPerformed
        LimpiarFiltro();
        mostrarDatos();

    }//GEN-LAST:event_BtnBorrarActionPerformed

    private void TxtNombreCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNombreCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNombreCActionPerformed

    private void TxtDireccionCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtDireccionCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtDireccionCActionPerformed

    private void TxtApellidoCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtApellidoCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtApellidoCActionPerformed

    private void TxtEntrecalleCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtEntrecalleCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtEntrecalleCActionPerformed

    public void codigos() {
        // Metodo para generar codigos automanticamente desde la clase GenerarCodigos     
        int j;
        int cont = 1;
        String num = "";
//        String c="";
        String SQL = "select max(cod_viajes) from labarrera.viajes";
        // String SQL="select count(*) from factura";
        //String SQL="SELECT MAX(cod_emp) AS cod_emp FROM empleado";
        //String SQL="SELECT @@identity AS ID";
        try {
            PreparedStatement pst = con.prepareStatement(SQL);
            ResultSet rs = pst.executeQuery(SQL);
            if (rs.next()) {
                num = rs.getString(1);
            }

            if (num == null) {
                TxtCod_v.setText("0001");
            }

            j = Integer.parseInt(num);
            GenerarCodigos gen = new GenerarCodigos();
            gen.generar(j);
            TxtCod_v.setText(gen.serie());

        } catch (SQLException ex) {
            Logger.getLogger(Panel_Viajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        int i; //EJECUTAMOS EL METODO GUARDAR DE LOS METODOS SQL)
        i = Met.guardarViajes(Integer.parseInt(TxtCod_v.getText()), TxtNombreC.getText(), TxtApellidoC.getText(), TxtDniC.getText(), TxtDireccionC.getText(),
                TxtNombrech.getText(), TxtApellidoch.getText(), TxtDnich.getText(), TxtMarcach.getText(), TxtDominioch.getText(),
                TxtOrigen.getText(), TxtDestino.getText(), TxtFecha.getText(), TxtHora.getText(), Double.parseDouble(TxtCosto.getText()), Double.parseDouble(TxtGasto.getText()), Double.parseDouble(TxtTotal.getText()), TxtComentario.getText());
        if (i > 0) {
            JOptionPane.showMessageDialog(this, "Datos guardados con exito.");

            mostrarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar esta duplicando datos.");

        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        codigos();
        limpiarCajas();
        LimpiarCostos();
        mostrarDatos();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        ViajesChofer ven = new ViajesChofer();
        ven.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void BtnCerrarViajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCerrarViajeActionPerformed
        //Esta linea de codigo es para capturar un valor de la celda y hacer comparacion
        DefaultTableModel tm = (DefaultTableModel) JTViajes.getModel();
        String dato = String.valueOf(tm.getValueAt(JTViajes.getSelectedRow(), 16));
        //condicional IF para saber si el valor capturado de la fila es "cero" entonces
        if (dato.equals("0")) {
            CerrarViaje ventana = new CerrarViaje();

            ventana.setVisible(true);

            String cod_v = "", nombreC = "", apellidoC = "", dniC = "", direccionC = "", nombreCh = "", apellidoCh = "", dniCh = "", marcaCh = "", dominioCh = "", origen = "", destino = "", fecha = "", hora = "", costo = "", gasto = "", comentario = "";
            int filaseleccionada = JTViajes.getSelectedRow();
            try {
                if (filaseleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "No ha seleccionado ningun dato");

                } else { //captamos los datos de las cajas y filas 
                    cod_v = (String) JTViajes.getValueAt(filaseleccionada, 0);
                    nombreC = (String) JTViajes.getValueAt(filaseleccionada, 1);
                    apellidoC = (String) JTViajes.getValueAt(filaseleccionada, 2);
                    dniC = (String) JTViajes.getValueAt(filaseleccionada, 3);
                    direccionC = (String) JTViajes.getValueAt(filaseleccionada, 4);
                    nombreCh = (String) JTViajes.getValueAt(filaseleccionada, 5);
                    apellidoCh = (String) JTViajes.getValueAt(filaseleccionada, 6);
                    dniCh = (String) JTViajes.getValueAt(filaseleccionada, 7);
                    marcaCh = (String) JTViajes.getValueAt(filaseleccionada, 8);
                    dominioCh = (String) JTViajes.getValueAt(filaseleccionada, 9);
                    origen = (String) JTViajes.getValueAt(filaseleccionada, 10);
                    destino = (String) JTViajes.getValueAt(filaseleccionada, 11);
                    fecha = (String) JTViajes.getValueAt(filaseleccionada, 12);
                    hora = (String) JTViajes.getValueAt(filaseleccionada, 13);
                    costo = (String) JTViajes.getValueAt(filaseleccionada, 14);
                    gasto = (String) JTViajes.getValueAt(filaseleccionada, 15);
                    comentario = (String) JTViajes.getValueAt(filaseleccionada, 17);

                    //Dato importante para que pueda recivir el envio de datos el Frm Panel_viajes las cajas deber ser public static
                    CerrarViaje.TxtCod_v.setText(cod_v);
                    CerrarViaje.TxtNombreC.setText(nombreC);
                    CerrarViaje.TxtApellidoC.setText(apellidoC);
                    CerrarViaje.TxtDniC.setText(dniC);
                    CerrarViaje.TxtDireccionC.setText(direccionC);
                    CerrarViaje.TxtNombrech.setText(nombreCh);
                    CerrarViaje.TxtApellidoch.setText(apellidoCh);
                    CerrarViaje.TxtDnich.setText(dniCh);

                    CerrarViaje.TxtMarcach.setText(marcaCh);
                    CerrarViaje.TxtDominioch.setText(dominioCh);
                    CerrarViaje.TxtOrigen.setText(origen);
                    CerrarViaje.TxtDestino.setText(destino);
                    CerrarViaje.TxtFecha.setText(fecha);
                    CerrarViaje.TxtHora.setText(hora);
                    CerrarViaje.TxtCosto.setText(costo);
                    CerrarViaje.TxtGasto.setText(gasto);
                    CerrarViaje.TxtComentario.setText(comentario);

                    LimpiarCostos();
                }
            } catch (Exception e) {

            }
            //sino que muestre este cartel
        } else {
            JOptionPane.showMessageDialog(null, "El viaje ya fue cerrado.");

        }


    }//GEN-LAST:event_BtnCerrarViajeActionPerformed

    private void JTViajesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTViajesMouseClicked

        int filaSeleccionada = JTViajes.rowAtPoint(evt.getPoint());

        TxtCod_v.setText(JTViajes.getValueAt(filaSeleccionada, 0).toString());
        TxtNombreC.setText(JTViajes.getValueAt(filaSeleccionada, 1).toString());
        TxtApellidoC.setText(JTViajes.getValueAt(filaSeleccionada, 2).toString());
        TxtDniC.setText(JTViajes.getValueAt(filaSeleccionada, 3).toString());
        TxtDireccionC.setText(JTViajes.getValueAt(filaSeleccionada, 4).toString());
        TxtNombrech.setText(JTViajes.getValueAt(filaSeleccionada, 5).toString());
        TxtApellidoch.setText(JTViajes.getValueAt(filaSeleccionada, 6).toString());
        TxtDnich.setText(JTViajes.getValueAt(filaSeleccionada, 7).toString());
        TxtMarcach.setText(JTViajes.getValueAt(filaSeleccionada, 8).toString());
        TxtDominioch.setText(JTViajes.getValueAt(filaSeleccionada, 9).toString());
        TxtOrigen.setText(JTViajes.getValueAt(filaSeleccionada, 10).toString());
        TxtDestino.setText(JTViajes.getValueAt(filaSeleccionada, 11).toString());
        TxtFecha.setText(JTViajes.getValueAt(filaSeleccionada, 12).toString());
        TxtHora.setText(JTViajes.getValueAt(filaSeleccionada, 13).toString());
        TxtCosto.setText(JTViajes.getValueAt(filaSeleccionada, 14).toString());
        TxtGasto.setText(JTViajes.getValueAt(filaSeleccionada, 15).toString());
        TxtTotal.setText(JTViajes.getValueAt(filaSeleccionada, 16).toString());
        TxtComentario.setText(JTViajes.getValueAt(filaSeleccionada, 17).toString());
    }//GEN-LAST:event_JTViajesMouseClicked

    private void TxtGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtGastoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtGastoActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        VerViaje ventana = new VerViaje();
        ventana.setVisible(true);

        String cod_v = "", nombreC = "", apellidoC = "", dniC = "", direccionC = "", nombreCh = "", apellidoCh = "", dniCh = "", marcaCh = "", dominioCh = "", origen = "", destino = "", fecha = "", hora = "", costo = "", gasto = "", total = "", comentario = "";
        int filaseleccionada = JTViajes.getSelectedRow();
        try {
            if (filaseleccionada == -1) {
                JOptionPane.showMessageDialog(null, "No ha seleccionado ningun dato");

            } else { //captamos los datos de las cajas y filas 
                cod_v = (String) JTViajes.getValueAt(filaseleccionada, 0);
                nombreC = (String) JTViajes.getValueAt(filaseleccionada, 1);
                apellidoC = (String) JTViajes.getValueAt(filaseleccionada, 2);
                dniC = (String) JTViajes.getValueAt(filaseleccionada, 3);
                direccionC = (String) JTViajes.getValueAt(filaseleccionada, 4);
                nombreCh = (String) JTViajes.getValueAt(filaseleccionada, 5);
                apellidoCh = (String) JTViajes.getValueAt(filaseleccionada, 6);
                dniCh = (String) JTViajes.getValueAt(filaseleccionada, 7);
                marcaCh = (String) JTViajes.getValueAt(filaseleccionada, 8);
                dominioCh = (String) JTViajes.getValueAt(filaseleccionada, 9);
                origen = (String) JTViajes.getValueAt(filaseleccionada, 10);
                destino = (String) JTViajes.getValueAt(filaseleccionada, 11);
                fecha = (String) JTViajes.getValueAt(filaseleccionada, 12);
                hora = (String) JTViajes.getValueAt(filaseleccionada, 13);
                costo = (String) JTViajes.getValueAt(filaseleccionada, 14);
                gasto = (String) JTViajes.getValueAt(filaseleccionada, 15);
                total = (String) JTViajes.getValueAt(filaseleccionada, 16);
                comentario = (String) JTViajes.getValueAt(filaseleccionada, 17);

                //Dato importante para que pueda recivir el envio de datos el Frm Panel_viajes las cajas deber ser public static
                VerViaje.TxtCod_v.setText(cod_v);
                VerViaje.TxtNombreC.setText(nombreC);
                VerViaje.TxtApellidoC.setText(apellidoC);
                VerViaje.TxtDniC.setText(dniC);
                VerViaje.TxtDireccionC.setText(direccionC);
                VerViaje.TxtNombrech.setText(nombreCh);
                VerViaje.TxtApellidoch.setText(apellidoCh);
                VerViaje.TxtDnich.setText(dniCh);

                VerViaje.TxtMarcach.setText(marcaCh);
                VerViaje.TxtDominioch.setText(dominioCh);
                VerViaje.TxtOrigen.setText(origen);
                VerViaje.TxtDestino.setText(destino);
                VerViaje.TxtFecha.setText(fecha);
                VerViaje.TxtHora.setText(hora);
                VerViaje.TxtCosto.setText(costo);
                VerViaje.TxtGasto.setText(gasto);
                VerViaje.TxtTotal.setText(total);
                VerViaje.TxtComentario.setText(comentario);

//                this.dispose();
            }
        } catch (Exception e) {
    }//GEN-LAST:event_jButton5ActionPerformed

    }
    private void TxtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtBuscarKeyReleased
        FiltrarFecha(TxtBuscar.getText());
    }//GEN-LAST:event_TxtBuscarKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        LimpiarCostos();
        LimpiarFiltro();
        mostrarDatos();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBorrar;
    private javax.swing.JButton BtnCerrarViaje;
    private javax.swing.JPanel JPDATOS;
    private javax.swing.JPanel JPPrimeraCapa;
    private javax.swing.JTable JTViajes;
    private javax.swing.JLabel LblFecha;
    private javax.swing.JLabel LblHora;
    public static javax.swing.JTextField TxtApellidoC;
    public static javax.swing.JTextField TxtApellidoch;
    private javax.swing.JTextField TxtBuscar;
    public static javax.swing.JTextField TxtCelTelch;
    public static javax.swing.JTextField TxtCodC;
    private javax.swing.JTextField TxtCod_v;
    public static javax.swing.JTextField TxtCodch;
    private javax.swing.JTextArea TxtComentario;
    private javax.swing.JTextField TxtCosto;
    private javax.swing.JTextField TxtDestino;
    public static javax.swing.JTextField TxtDireccionC;
    public static javax.swing.JTextField TxtDniC;
    public static javax.swing.JTextField TxtDnich;
    public static javax.swing.JTextField TxtDominioch;
    public static javax.swing.JTextField TxtDueñoch;
    public static javax.swing.JTextField TxtEntrecalleC;
    private javax.swing.JLabel TxtFecha;
    private javax.swing.JTextField TxtGasto;
    private javax.swing.JLabel TxtHora;
    public static javax.swing.JTextField TxtMarcach;
    public static javax.swing.JTextField TxtModeloch;
    public static javax.swing.JTextField TxtNombreC;
    public static javax.swing.JTextField TxtNombrech;
    private javax.swing.JTextField TxtOrigen;
    private javax.swing.JTextField TxtTotal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JEditorPane jEditorPane2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
