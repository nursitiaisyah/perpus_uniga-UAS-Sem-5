/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package perpus_uniga;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author ASUS VIVOBOOK
 */
public class FDataPengembalian extends javax.swing.JFrame {

    /**
     * Creates new form FDataPengembalian
     */
    public FDataPengembalian() {
        initComponents();
        load_data(); //Memanggil Menampilkan Data
        IDOtomatis();
        LoadIDPeminjaman();
    }
    
    private void load_data()
    {
        Connection kon=koneksi.koneksiDb();
        Object header[]={"ID PENGEMBALIAN","ID PEMINJAMAN","JENIS DENDA","STATUS PENGEMBALIAN"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        TabelPengembalian.setModel (data);
        String sql_data = "SELECT * FROM t_pengembalian";
        try
        {
            Statement st=kon.createStatement();
            ResultSet rs=st.executeQuery(sql_data);
            while(rs.next())
            {
                String d1=rs.getString(1);
                String d2=rs.getString(2);
                String d3=rs.getString(3);
                String d4=rs.getString(4);
                
                
                String d[]={d1,d2,d3,d4};
                data.addRow(d);
            
            }
        }
        catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
    }
    
    //ID Peminjaman Otomatis
    private void IDOtomatis()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_idp="SELECT * FROM t_pengembalian order by id_pengembalian desc";
            ResultSet rs=st.executeQuery(sql_idp);
            if(rs.next())
            {
                String id_pengembalian=rs.getString("id_pengembalian").substring(1);
                String AN=""+(Integer.parseInt(id_pengembalian)+1);
                String No1="";
                if(AN.length()==1) //Jika id_anggota A00001
                {
                    No1="0000";
                }
                else if(AN.length()==2) //Jika id_anggota A00010
                {
                    No1="000";
                }
                else if(AN.length()==3) //Jika id_anggota A00100
                {
                    No1="00";
                }
                IDPENGEMBALIAN.setText("K"+No1+AN);
            }
            else
            {
                IDPENGEMBALIAN.setText("K00001");
            }
        }
        catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,e);
                }
    }
    
    //LOAD COMBOBOX ID PEMINJAMAN
    private void LoadIDPeminjaman()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            String sql_load_combo="SELECT * FROM t_peminjaman";
            ResultSet rs=st.executeQuery(sql_load_combo);
            while(rs.next())
            {
                IDPEMINJAMAN.addItem(rs.getString("id_peminjaman"));
            }
            rs.close();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //INPUT DATA-----------------------------------------------------------
    private void input_data()
    {
        try
        {
        Connection kon=koneksi.koneksiDb();
        Statement st=kon.createStatement(); 
        // Validasi - pastikan semua kolom terisi
        if (IDPENGEMBALIAN.getText().isEmpty() || IDPEMINJAMAN.getSelectedItem() == null 
                || JENIS.getSelectedItem() == null 
                || STATUS.getSelectedItem() == null ) {
            JOptionPane.showMessageDialog(null, "Harap isi semua kolom sebelum menambahkan data.");
            return; // Jika ada yang kosong, hentikan eksekusi
        }
        
        String selectedPeminjaman = IDPEMINJAMAN.getSelectedItem().toString();
        String selectedJenis = JENIS.getSelectedItem().toString();
        String selectedStatusPeminjaman = STATUS.getSelectedItem().toString();
        
        String sql="INSERT INTO t_pengembalian values('"+IDPENGEMBALIAN.getText()
                +"','"+selectedPeminjaman
                +"','"+selectedJenis
                +"','"+selectedStatusPeminjaman
                +"')";
        st.execute(sql);
        JOptionPane.showMessageDialog(null, "Data Peminjaman Berhasil Dimasukkan");
        }
        catch(Exception e)
            {
               JOptionPane.showMessageDialog(null, e);
            }
    }
    
     //Reset Data Setelah Input/Edit/Delete 
   public void clear() {
    // Reset combobox for IDANGGOTA
    IDPEMINJAMAN.setSelectedItem("");


    // Reset combobox for JENIS
    JENIS.setSelectedItem("FAR");
    
    // Reset combobox for STATUSPINJAM
    STATUS.setSelectedItem("FAR");
}
   
   //Edit Data
    public void update()
    {
        try
        {
            Connection kon=koneksi.koneksiDb();
            Statement st=kon.createStatement();
            
            String sql_update="UPDATE t_pengembalian SET id_peminjaman='"+IDPEMINJAMAN.getSelectedItem()
                    +"',jenis_denda='"+JENIS.getSelectedItem()
                    +"',status_pengembalian='"+STATUS.getSelectedItem()
                    +"'WHERE id_pengembalian='"+IDPENGEMBALIAN.getText()+"'";
            st.execute(sql_update);
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
     //Delete Data Petugas
    private void delete()
    {
        try
        {
          Connection kon=koneksi.koneksiDb();
          Statement st=kon.createStatement();  
          String sql_delete="DELETE from t_pengembalian WHERE "
                  +"id_pengembalian='"+IDPENGEMBALIAN.getText()+"'";
          st.executeUpdate(sql_delete);
          JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        IDPENGEMBALIAN = new javax.swing.JTextField();
        IDPEMINJAMAN = new javax.swing.JComboBox<>();
        JENIS = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelPengembalian = new javax.swing.JTable();
        BKELUAR = new javax.swing.JButton();
        BINPUT = new javax.swing.JButton();
        BEDIT = new javax.swing.JButton();
        BDELETE = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        STATUS = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Pengembalian");

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("KELOLA DATA PENGEMBALIAN");

        jLabel1.setText("ID PENGEMBALIAN");

        jLabel3.setText("ID PEMINJAMAN");

        jLabel4.setText("JENIS DENDA");

        IDPENGEMBALIAN.setEnabled(false);

        JENIS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak Ada", "Hilang", "Rusak" }));

        TabelPengembalian.setModel(new javax.swing.table.DefaultTableModel(
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
        TabelPengembalian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelPengembalianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelPengembalian);

        BKELUAR.setText("KELUAR");
        BKELUAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BKELUARActionPerformed(evt);
            }
        });

        BINPUT.setText("INPUT");
        BINPUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BINPUTActionPerformed(evt);
            }
        });

        BEDIT.setText("EDIT");
        BEDIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BEDITActionPerformed(evt);
            }
        });

        BDELETE.setText("DELETE");
        BDELETE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BDELETEActionPerformed(evt);
            }
        });

        jLabel5.setText("STATUS PENGEMBALIAN");

        STATUS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Berlangsung", "Selesai", "Terlambat" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(228, 228, 228))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BKELUAR)
                        .addGap(18, 18, 18)
                        .addComponent(BINPUT)
                        .addGap(18, 18, 18)
                        .addComponent(BEDIT)
                        .addGap(18, 18, 18)
                        .addComponent(BDELETE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(IDPENGEMBALIAN, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JENIS, 0, 140, Short.MAX_VALUE)
                            .addComponent(IDPEMINJAMAN, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(STATUS, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(IDPENGEMBALIAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(IDPEMINJAMAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(JENIS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(STATUS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BKELUAR)
                    .addComponent(BINPUT)
                    .addComponent(BEDIT)
                    .addComponent(BDELETE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BKELUARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BKELUARActionPerformed
        int keluar;
        keluar = JOptionPane.showOptionDialog(this,
                "Keluar dari Kelola Data Anggota?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null,null);
        if(keluar==JOptionPane.YES_NO_OPTION)
        {
        new FUtama() .show();
        this.dispose();
        }
    }//GEN-LAST:event_BKELUARActionPerformed

    private void BEDITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BEDITActionPerformed
        int update = JOptionPane.showOptionDialog(this,
                "Apakah Data Akan di Update?",
                "Update Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(update==JOptionPane.YES_OPTION)
        {
        update();
        clear();
        load_data();
        IDOtomatis();
        //Set Enable INPUT
        BINPUT.setEnabled(false);
        BEDIT.setEnabled(true);
        BDELETE.setEnabled(true); 
        }
    }//GEN-LAST:event_BEDITActionPerformed

    private void BDELETEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BDELETEActionPerformed
        // TODO add your handling code here:
    int delete = JOptionPane.showOptionDialog(this,
                "Apakah Data Akan Di Hapus?",
                "Hapus Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null,null);
        if(delete==JOptionPane.YES_NO_OPTION)
        {
        delete();
        clear();
        load_data();
        IDOtomatis();
        //Set Enablet INPUT
        BINPUT.setEnabled(true);
        BEDIT.setEnabled(false);
        BDELETE.setEnabled(false);
        }   
    }//GEN-LAST:event_BDELETEActionPerformed

    private void TabelPengembalianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelPengembalianMouseClicked
        int bar=TabelPengembalian.getSelectedRow();
        String a=TabelPengembalian.getValueAt(bar, 0).toString();
        String b=TabelPengembalian.getValueAt(bar, 1).toString();
        String c=TabelPengembalian.getValueAt(bar, 2).toString();
        String d=TabelPengembalian.getValueAt(bar, 3).toString();
        
        IDPENGEMBALIAN.setText(a);
        IDPEMINJAMAN.setSelectedItem(b);
        JENIS.setSelectedItem(c);
        STATUS.setSelectedItem(d);
        
        //Set Disable Input
        BINPUT.setEnabled(false);
        BEDIT.setEnabled(true);
        BDELETE.setEnabled(true); 
    }//GEN-LAST:event_TabelPengembalianMouseClicked

    private void BINPUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BINPUTActionPerformed
        // TODO add your handling code here:
         int simpan = JOptionPane.showOptionDialog(this,
                "Apakah Data Sudah Benar?",
                "Simpan Data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(simpan==JOptionPane.YES_OPTION)
        {
            input_data();
            load_data();
            clear();
            IDOtomatis();
        }
    }//GEN-LAST:event_BINPUTActionPerformed

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
            java.util.logging.Logger.getLogger(FDataPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FDataPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FDataPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FDataPengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FDataPengembalian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BDELETE;
    private javax.swing.JButton BEDIT;
    private javax.swing.JButton BINPUT;
    private javax.swing.JButton BKELUAR;
    private javax.swing.JComboBox<String> IDPEMINJAMAN;
    private javax.swing.JTextField IDPENGEMBALIAN;
    private javax.swing.JComboBox<String> JENIS;
    private javax.swing.JComboBox<String> STATUS;
    private javax.swing.JTable TabelPengembalian;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
