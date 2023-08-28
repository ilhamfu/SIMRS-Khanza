/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package inventory;

import document_filter.NumberOnlyFilter;
import document_filter.SimpleDocumentListener;
import event.EventBus;
import event.EventListener;
import event.eventList.AturanPakaiSelectedEvent;
import fungsi.koneksiDB;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AbstractDocument;
import model.Obat;
import model.RacikanDetail;

class RacikanDetailTableModel extends AbstractTableModel {
    
    private int jumlah = 1;
    
    public String[] COLUMN = new String[]{
        "Kode Barang",
        "Nama Barang",
        "Satuan",
        "Harga(Rp)",
        "H.Beli",
        "Jenis Obat",
        "Stok",
        "Kps",
        "P1",
        "P2",
        "Kandungan",
        "Jml",
        "I.F.",
        "Komposisi",
        "Hapus"
    };
    
    public void setJumlah(int i) {
        this.jumlah = i;
        this.fireTableDataChanged();
    }
    
    public Class[] COLUMN_CLASS = new Class[]{
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.Double.class,
        java.lang.Double.class,
        java.lang.String.class,
        java.lang.Double.class,
        java.lang.Double.class,
        java.lang.Double.class,
        java.lang.Double.class,
        java.lang.Double.class,
        java.lang.Double.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,};
    
    private List<RacikanDetail> list;
    
    public RacikanDetailTableModel() {
        list = new ArrayList();
    }
    
    public void setList(List<RacikanDetail> list) {
        this.list = list;
        this.fireTableDataChanged();
    }
    
    public void addDetail(Obat obat) {
        this.list.add(
                new RacikanDetail(obat)
        );
        this.fireTableRowsInserted(this.list.size() - 1, this.list.size() - 1);
    }
    
    public void removeDetail(int rowIndex) {
        this.list.remove(rowIndex);
        this.fireTableRowsDeleted(rowIndex, rowIndex);
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_CLASS[columnIndex];
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMN[column];
    }
    
    @Override
    public int getRowCount() {
        return this.list.size();
    }
    
    @Override
    public int getColumnCount() {
        return this.COLUMN.length;
    }
    
    public RacikanDetail getObatAt(int rowIndex) {
        return this.list.get(rowIndex);
    }
    
    private Object[] getRow(int rowIndex) {
        RacikanDetail detail = this.getObatAt(rowIndex);
        return new Object[]{
            detail.obat.kd_barang,
            detail.obat.nm_barang,
            detail.obat.kd_satuan,
            detail.obat.getHargaJual(0.1),
            detail.obat.hrg_beli,
            detail.obat.nm_jenis,
            detail.obat.stok,
            detail.obat.kapasitas,
            detail.p1,
            detail.p2,
            detail.kandungan,
            Math.ceil(jumlah * (detail.p1 / detail.p2)),
            detail.obat.nm_industri,
            "",
            ""
        };
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object[] row = this.getRow(rowIndex);
        return row[columnIndex];
        
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 8:
            case 9:
            case 10:
                return true;
        }
        return false;
    }
    
    @Override
    public void setValueAt(Object val, int rowIndex, int columnIndex) {
        RacikanDetail detail = this.getObatAt(rowIndex);
        switch (columnIndex) {
            case 8:
                detail.setP1((double) val);
                break;
            case 9:
                detail.setP2((double) val);
                break;
            case 10:
                detail.setKandungan((double) val);
                break;
        }
        this.fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
}

class ObatTableModel extends AbstractTableModel {
    
    private List<Obat> list;
    
    public String[] COLUMN = new String[]{
        "Kode Barang", "Nama Barang", "Satuan", "Harga(Rp)", "H.Beli",
        "Jenis Obat", "Stok", "Kps"
    };
    
    public Class[] COLUMN_CLASS = new Class[]{
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.Double.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.Double.class,
        java.lang.Double.class};
    
    public ObatTableModel() {
        list = new ArrayList();
    }
    
    public void setList(List<Obat> list) {
        this.list = list;
        this.fireTableDataChanged();
    }
    
    public Obat getObatAt(int rowIndex) {
        return this.list.get(rowIndex);
    }
    
    private Object[] getRow(int rowIndex) {
        Obat obat = this.getObatAt(rowIndex);
        return new Object[]{
            obat.kd_barang,
            obat.nm_barang,
            obat.kd_satuan,
            obat.getHargaJual(0.1),
            obat.hrg_beli,
            obat.nm_jenis,
            obat.stok,
            obat.kapasitas
        };
        
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }
    
    @Override
    public int getColumnCount() {
        return COLUMN.length;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_CLASS[columnIndex];
    }
    
    @Override
    public String getColumnName(int column) {
        return this.COLUMN[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.getRow(rowIndex)[columnIndex];
    }
    
}

class MyButton extends JButton implements TableCellRenderer {
    
    RacikanDetailTableModel model;
    
    public MyButton(RacikanDetailTableModel model) {
        super();
        this.model = model;
        this.setOpaque(true);
        
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText("::Hapus::");
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.removeDetail(row);
            }
        });
        return this;
    }
    
}

/**
 *
 * @author hp2k
 */
public class DlgDetailRacikanDokter extends javax.swing.JDialog {
    
    private final ObatTableModel tmdlObat = new ObatTableModel();
    private final RacikanDetailTableModel tmdlRacikanDetail = new RacikanDetailTableModel();
    private final Connection koneksi = koneksiDB.condb();
    
    private EventBus bus;

    /**
     * Creates new form DlgDetailRacikanDokter
     */
    public DlgDetailRacikanDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tblObat.setModel(tmdlObat);
        tblDetailRacikan.setModel(tmdlRacikanDetail);
        tblDetailRacikan.getColumnModel().getColumn(14).setCellRenderer(new MyButton(tmdlRacikanDetail));
        
        tbJumlah.setText("1");
        tmdlRacikanDetail.setJumlah(1);
        
        this.bus = new EventBus();
        
        bus.subscribe(AturanPakaiSelectedEvent.class, new EventListener<AturanPakaiSelectedEvent>() {
            @Override
            public void onEvent(AturanPakaiSelectedEvent item) {
                tbMetode.setText(item.getItem());
            }
        });
        
        ((AbstractDocument) tbJumlah.getDocument()).setDocumentFilter(new NumberOnlyFilter());
        tbJumlah.addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void onChange(DocumentEvent e) {
                int foo;
                try {
                    foo = Integer.parseInt(tbJumlah.getText());
                } catch (NumberFormatException errr) {
                    foo = 0;
                }
                
                tmdlRacikanDetail.setJumlah(foo);
            }
        });
        
    }
    
    void cariObat(String obat) {
        final boolean is_batch = true;
        final double kenaikan = 0.1;
        
        final String query
                = "SELECT "
                + "b.kode_brng as kd_barang,"
                + "b.nama_brng as nm_barang,"
                + "j.nama as nm_jenis,"
                + "b.kode_sat as kd_satuan,"
                + "b.karyawan as hrg_karyawan,"
                + "b.ralan as hrg_ralan,"
                + "b.kelas1 as hrg_kelas1,"
                + "b.kelas2 as hrg_kelas2,"
                + "b.kelas3 as hrg_kelas3,"
                + "b.kapasitas as kapasitas,"
                + "b.vip as hrg_vip,"
                + "b.vvip as hrg_vvip,"
                + "b.beliluar as hrg_beliluar,"
                + "b.letak_barang as letak_barang,"
                + "b.utama as is_utama,"
                + "f.nama_industri as nm_industri,"
                + "b.h_beli as hrg_beli,"
                + (is_batch ? "sum(gb.stok)" : "gb.stok") + " as stok "
                + "FROM databarang b "
                + "INNER JOIN jenis j ON b.kdjns = j.kdjns "
                + "INNER JOIN gudangbarang gb ON b.kode_brng = gb.kode_brng "
                + "INNER JOIN industrifarmasi f ON f.kode_industri=b.kode_industri "
                + "WHERE "
                + "b.status='1' "
                + "AND gb.stok>0 "
                + "AND gb.no_faktur<>'' "
                + "AND gb.no_batch " + (is_batch ? "<>" : "=") + "'' "
                + "AND gb.kd_bangsal=? " // 1
                + "AND ("
                + "b.kode_brng like ? " // 2
                + "or b.nama_brng like ? " //3
                + "or j.nama like ? "// 4
                + "OR b.letak_barang like ? ) "//5
                + (is_batch ? "GROUP BY gb.kode_brng " : "")
                + "ORDER BY b.nama_brng";
        try {
            PreparedStatement stmt = koneksi.prepareStatement(query);
            stmt.setString(1, "AP");
            stmt.setString(2, "%" + obat + "%");
            stmt.setString(3, "%" + obat + "%");
            stmt.setString(4, "%" + obat + "%");
            stmt.setString(5, "%" + obat + "%");
            
            ResultSet rs = stmt.executeQuery();
            
            List<Obat> obats = Obat.arrayFromResultSet(rs);
            
            rs.close();
            stmt.close();
            
            tmdlObat.setList(obats);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    public void setMetode(String metode) {
        this.tbMetode.setText(metode);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        internalFrame1 = new widget.InternalFrame();
        panelBiasa1 = new widget.PanelBiasa();
        label1 = new widget.Label();
        tbNoResep = new widget.TextBox();
        label2 = new widget.Label();
        label3 = new widget.Label();
        label4 = new widget.Label();
        tbNoRacikan = new widget.TextBox();
        tbJumlah = new widget.TextBox();
        tbKeterangan = new widget.TextBox();
        tbMetode = new widget.TextBox();
        label5 = new widget.Label();
        btnMetode = new widget.Button();
        panelBiasa2 = new widget.PanelBiasa();
        scrollPane1 = new widget.ScrollPane();
        tblObat = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tblDetailRacikan = new widget.Table();
        panelisi4 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnAll = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "::[Detail Racikan Obat Dokter]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 0))); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout());

        panelBiasa1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        panelBiasa1.setLayout(new java.awt.GridBagLayout());

        label1.setText("No Resep");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        panelBiasa1.add(label1, gridBagConstraints);

        tbNoResep.setToolTipText("");
        tbNoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbNoResepActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        panelBiasa1.add(tbNoResep, gridBagConstraints);

        label2.setText("No Racikan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 6, 8, 0);
        panelBiasa1.add(label2, gridBagConstraints);

        label3.setText("Jumlah");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        panelBiasa1.add(label3, gridBagConstraints);

        label4.setText("Keterangan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        panelBiasa1.add(label4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        panelBiasa1.add(tbNoRacikan, gridBagConstraints);

        tbJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbJumlahActionPerformed(evt);
            }
        });
        tbJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tbJumlahKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.4;
        panelBiasa1.add(tbJumlah, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        panelBiasa1.add(tbKeterangan, gridBagConstraints);

        tbMetode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbMetodeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.4;
        panelBiasa1.add(tbMetode, gridBagConstraints);

        label5.setText("Metode");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 1);
        panelBiasa1.add(label5, gridBagConstraints);

        btnMetode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnMetode.setMnemonic('1');
        btnMetode.setToolTipText("ALt+1");
        btnMetode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMetodeActionPerformed(evt);
            }
        });
        btnMetode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnMetodeKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        panelBiasa1.add(btnMetode, gridBagConstraints);

        internalFrame1.add(panelBiasa1, java.awt.BorderLayout.PAGE_START);

        panelBiasa2.setLayout(new java.awt.BorderLayout());

        tblObat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblObatMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tblObat);

        panelBiasa2.add(scrollPane1, java.awt.BorderLayout.PAGE_END);

        tblDetailRacikan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollPane2.setViewportView(tblDetailRacikan);

        panelBiasa2.add(scrollPane2, java.awt.BorderLayout.CENTER);

        internalFrame1.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        panelisi4.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Key Word :");
        label10.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi4.add(label10);

        TCari.setToolTipText("Alt+C");
        TCari.setPreferredSize(new java.awt.Dimension(245, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi4.add(TCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi4.add(BtnAll);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi4.add(BtnSimpan);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('5');
        BtnKeluar.setToolTipText("Alt+5");
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi4.add(BtnKeluar);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        cariObat("");
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed

    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed

    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void tbNoResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbNoResepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbNoResepActionPerformed

    private void tblObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblObatMouseClicked
        if (evt.getClickCount() != 2 || evt.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        if (tblObat.getSelectedRow() < 0) {
            return;
        }
        
        tmdlRacikanDetail.addDetail(tmdlObat.getObatAt(tblObat.getSelectedRow()));
    }//GEN-LAST:event_tblObatMouseClicked

    private void tbJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbJumlahActionPerformed

    }//GEN-LAST:event_tbJumlahActionPerformed

    private void tbJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJumlahKeyTyped
        int foo;
        try {
            foo = Integer.parseInt(tbJumlah.getText());
        } catch (NumberFormatException e) {
            foo = 0;
        }
        
        this.tmdlRacikanDetail.setJumlah(foo);
    }//GEN-LAST:event_tbJumlahKeyTyped

    private void tbMetodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbMetodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbMetodeActionPerformed

    private void btnMetodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMetodeActionPerformed
        final DlgAturanPakaiCom dlgAturan = new DlgAturanPakaiCom(this, false, this.bus);
        dlgAturan.setLocationRelativeTo(null);
        dlgAturan.setVisible(true);
    }//GEN-LAST:event_btnMetodeActionPerformed

    private void btnMetodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnMetodeKeyPressed

    }//GEN-LAST:event_btnMetodeKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DlgDetailRacikanDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgDetailRacikanDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgDetailRacikanDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgDetailRacikanDokter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgDetailRacikanDokter dialog = new DlgDetailRacikanDokter(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.TextBox TCari;
    private widget.Button btnMetode;
    private widget.InternalFrame internalFrame1;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label2;
    private widget.Label label3;
    private widget.Label label4;
    private widget.Label label5;
    private widget.PanelBiasa panelBiasa1;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.TextBox tbJumlah;
    private widget.TextBox tbKeterangan;
    private widget.TextBox tbMetode;
    private widget.TextBox tbNoRacikan;
    private widget.TextBox tbNoResep;
    private widget.Table tblDetailRacikan;
    private widget.Table tblObat;
    // End of variables declaration//GEN-END:variables
}
