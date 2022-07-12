package org.josuevalle.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.josuevalle.bean.Administracion;
import org.josuevalle.bean.CuentaPorPagar;
import org.josuevalle.bean.Proveedor;
import org.josuevalle.db.Conexion;
import org.josuevalle.system.Principal;

public class CuentaPorPagarController implements Initializable {
    private enum operaciones{NUEVO, GUARDAR,ACTUALIZAR, NINGUNO}
    private operaciones tipoDeOperacion =operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<CuentaPorPagar> listaCuentaPorPagar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Proveedor> listaProveedores;
    private DatePicker fechaLimite;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCuentaPorPagar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtEstadoPago;
    @FXML private TextField txtValorNetoPago;
    @FXML private ComboBox cmbCodAdministracion;
    @FXML private ComboBox cmbCodProveedores;
    @FXML private TableView tblCuentasPorPagar;
    @FXML private TableColumn colCodigoCuentaPorPagar;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colFechaLimitePago;
    @FXML private TableColumn colEstadoPago;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colCodAdministracion;
    @FXML private TableColumn colCodProveedores;
    @FXML private GridPane grpFechaLimite;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fechaLimite = new DatePicker(Locale.ENGLISH);
        fechaLimite.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaLimite.getCalendarView().todayButtonTextProperty().set("Today");
        fechaLimite.getCalendarView().setShowWeeks(false);
        grpFechaLimite.add(fechaLimite, 5, 0);
        fechaLimite.getStylesheets().add("/org/josuevalle/resource/DatePicker.css");
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblCuentasPorPagar.setItems(getCuentaPorPagar());
        colCodigoCuentaPorPagar.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, Integer>("codigoCuentaPorPagar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, String>("numeroFactura"));
        colFechaLimitePago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, Date>("fechaLimitePago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, String>("estadoPago"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, Double>("valorNetoPago"));
        colCodAdministracion.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, Integer>("codigoAdministracion"));
        colCodProveedores.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar, Integer>("codigoProveedor"));
        cmbCodAdministracion.setItems(getAdministracion());
        cmbCodProveedores.setItems(getProveedor());
        
    }
    
    public void seleccionarElemento(){
         if(tblCuentasPorPagar.getSelectionModel().getSelectedItem()!= null){
        txtCodigoCuentaPorPagar.setText(String.valueOf(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorPagar()));
        txtNumeroFactura.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getNumeroFactura());
        fechaLimite.selectedDateProperty().set(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getFechaLimitePago());
        txtEstadoPago.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getEstadoPago());
        txtValorNetoPago.setText(String.valueOf(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
        cmbCodAdministracion.getSelectionModel().select(buscarAdministracion(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        cmbCodProveedores.getSelectionModel().select(buscarProveedor(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
         }
    }
    
    public ObservableList<CuentaPorPagar> getCuentaPorPagar(){
        ArrayList<CuentaPorPagar> lista = new ArrayList<CuentaPorPagar>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorPagar()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new CuentaPorPagar(resultado.getInt("codigoCuentaPorPagar"),
                                             resultado.getString("numeroFactura"),
                                             resultado.getDate("fechaLimitePago"),
                                             resultado.getString("estadoPago"),
                                             resultado.getDouble("valorNetoPago"),
                                             resultado.getInt("codigoAdministracion"),
                                             resultado.getInt("codigoProveedor")));
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaCuentaPorPagar = FXCollections.observableArrayList(lista);
    
    }
    
      public ObservableList<Administracion> getAdministracion(){
       ArrayList<Administracion> lista = new ArrayList<Administracion>();
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministracion()}");
           ResultSet resultado = procedimiento.executeQuery();
           while(resultado.next()){
               lista.add(new Administracion(resultado.getInt("codigoAdministracion"),
                            resultado.getString("direccion"),
                            resultado.getString("telefono")));
           }
           
       }catch(Exception e){
           e.printStackTrace();
       }
        
        return listaAdministracion = FXCollections.observableArrayList(lista);
        
    }
      
      
      public Administracion buscarAdministracion(int codigoAdministracion){
       Administracion resultado = null;
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarAdministracion(?)}");
           procedimiento.setInt(1, codigoAdministracion);
           ResultSet registro = procedimiento.executeQuery();
           while (registro.next()){
               resultado = new Administracion(registro.getInt("codigoAdministracion"),
                                             registro.getString("direccion"),
                                             registro.getString("telefono"));
           }
       }catch(Exception e){
           e.printStackTrace();
       }
       return resultado;
   }
      
       public ObservableList<Proveedor> getProveedor(){
      ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
      try{
          PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProveedores}");
          ResultSet resultado = procedimiento.executeQuery();
          while(resultado.next()){
              lista.add(new Proveedor(resultado.getInt("codigoProveedor"),
                                                  resultado.getString("NITProveedor"),
                                                  resultado.getString("servicioPrestado"),
                                                  resultado.getString("telefonoProveedor"),
                                                  resultado.getString("direccionProveedor"),
                                                  resultado.getDouble("saldoFavor"),
                                                  resultado.getDouble("saldoContra"),
                                                  resultado.getInt("codigoAdministracion")));
          }
      }catch(Exception e){
          e.printStackTrace();
      }
      return listaProveedores = FXCollections.observableArrayList(lista);
    }
    
    public Proveedor buscarProveedor(int codigoProveedor){
        Proveedor resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance(). getConexion().prepareCall("{call sp_BuscarProveedores(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                 resultado = new Proveedor(registro.getInt("codigoProveedor"),
                                            registro.getString("NITProveedor"),
                                            registro.getString("servicioPrestado"),
                                            registro.getString("telefonoProveedor"),
                                            registro.getString("direccionProveedor"),
                                            registro.getDouble("saldoContra"),
                                            registro.getDouble("saldoFavor"),
                                            registro.getInt("codigoAdministracion"));
                                            
        
            }
        }catch(Exception e){
            e.printStackTrace();
        
        }
        
        return resultado;
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/josuevalle/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/josuevalle/images/cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
            guardar();
            desactivarControles();
            limpiarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            imgNuevo.setImage(new Image("/org/josuevalle/images/agregar.png"));
            imgEliminar.setImage(new Image("/org/josuevalle/images/eliminar.png"));
            tipoDeOperacion = operaciones.NINGUNO;
            cargarDatos();
            break;
        }
   }
    
    public void guardar(){
        CuentaPorPagar registro = new CuentaPorPagar();
        registro.setNumeroFactura(txtNumeroFactura.getText());
        registro.setFechaLimitePago(fechaLimite.getSelectedDate());
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setCodigoAdministracion(((Administracion)cmbCodAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoProveedor(((Proveedor)cmbCodProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentasPorPagar(?,?,?,?,?,?)}");
            procedimiento.setString(1,registro.getNumeroFactura());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(3, registro.getEstadoPago());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setInt(5, registro.getCodigoAdministracion());
            procedimiento.setInt(6, registro.getCodigoProveedor());
            procedimiento.execute();
            listaCuentaPorPagar.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public void eliminar(){
       switch(tipoDeOperacion){
           case GUARDAR:
               desactivarControles();
               limpiarControles();
               btnNuevo.setText("Nuevo");
               btnEliminar.setText("Eliminar");
               imgNuevo.setImage(new Image("/org/josuevalle/images/agregar.png"));
               imgEliminar.setImage(new Image("/org/josuevalle/images/eliminar.png"));
               btnEditar.setDisable(false);
               btnReporte.setDisable(false);
               tipoDeOperacion = operaciones.NINGUNO;
               break;
           default:
               if (tblCuentasPorPagar.getSelectionModel().getSelectedItem()!=null){
                   int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Cuentas Por Pagar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                   if (respuesta == JOptionPane.YES_OPTION)
                   try{
                       PreparedStatement procedimiento =    Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentasPorPagar(?)}");
                       procedimiento.setInt(1,((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentaPorPagar());
                       procedimiento.execute();
                       listaCuentaPorPagar.remove(tblCuentasPorPagar.getSelectionModel().getSelectedIndex());
                       limpiarControles();
                   }catch(Exception e){
                       e.printStackTrace();
                   }   
               }else{
                   JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
               }
       }
   }
    
    public void editar(){
       switch(tipoDeOperacion){
           case NINGUNO:
               if(tblCuentasPorPagar.getSelectionModel().getSelectedItem()!=null){
                   desactivarComboBox();
                   btnEditar.setText("Actualizar");
                   btnReporte.setText("Cancelar");
                   btnNuevo.setDisable(true);
                   btnEliminar.setDisable(true);
                   imgEditar.setImage(new Image("/org/josuevalle/images/actualizar.png"));
                   imgReporte.setImage(new Image("/org/josuevalle/images/cancelar.png"));
                   desactivarComboBox();
                   tipoDeOperacion=operaciones.ACTUALIZAR;
               }else{
                   JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
               }
               
               break;
           case ACTUALIZAR:
                   actualizar();
                   btnNuevo.setDisable(false);
                   btnEliminar.setDisable(false);
                   btnEditar.setText("Editar");
                   btnReporte.setText("Reporte");
                   imgEditar.setImage(new Image("/org/josuevalle/images/editar.png"));
                   imgReporte.setImage(new Image("/org/josuevalle/images/Reporte.png"));
                   limpiarControles();
                   desactivarControles();
                   cargarDatos();
                   tipoDeOperacion = operaciones.NINGUNO;
                   break;
       }
   } 
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentasPorPagar(?,?,?,?,?)}");
            CuentaPorPagar registro = (CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setFechaLimitePago(fechaLimite.getSelectedDate());
            registro.setEstadoPago(txtEstadoPago.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            procedimiento.setInt(1, registro.getCodigoCuentaPorPagar());
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(4, registro.getEstadoPago());
            procedimiento.setDouble(5, registro.getValorNetoPago());
            procedimiento.execute();
            cargarDatos();
            tipoDeOperacion = operaciones.NINGUNO;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public void reporte(){
       switch(tipoDeOperacion){
               case ACTUALIZAR:
                   desactivarControles();
                   limpiarControles();
                   btnNuevo.setDisable(false);
                   btnEliminar.setDisable(false);
                   btnEditar.setText("Editar");
                   btnReporte.setText("Reporte");
                   imgEditar.setImage(new Image("/org/josuevalle/images/editar.png"));
                   imgReporte.setImage(new Image("/org/josuevalle/images/Reporte.png"));
                   tipoDeOperacion = operaciones.NINGUNO;
                  break;
       }
   }
    
    
    public void desactivarControles(){
        txtCodigoCuentaPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        fechaLimite.setDisable(true);
        txtEstadoPago.setEditable(false);
        txtValorNetoPago.setEditable(false);
        cmbCodAdministracion.setDisable(true);
        cmbCodProveedores.setDisable(true);
    }
    
    public void activarControles(){
       txtCodigoCuentaPorPagar.setEditable(false);
       txtNumeroFactura.setEditable(true);
       fechaLimite.setDisable(false);
       txtEstadoPago.setEditable(true);
       txtValorNetoPago.setEditable(true);
       cmbCodAdministracion.setDisable(false);
       cmbCodProveedores.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoCuentaPorPagar.clear();
        txtNumeroFactura.clear();
        fechaLimite.setPromptText("");
        txtEstadoPago.clear();
        txtValorNetoPago.clear();
        cmbCodAdministracion.setValue(null);
        cmbCodProveedores.setValue(null);
        
    }
    
    public void desactivarComboBox(){
       txtCodigoCuentaPorPagar.setEditable(false);
       txtNumeroFactura.setEditable(true);
       fechaLimite.setDisable(false);
       txtEstadoPago.setEditable(true);
       txtValorNetoPago.setEditable(true);
       cmbCodAdministracion.setDisable(true);
       cmbCodProveedores.setDisable(true);
    }
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaProveedores(){
          escenarioPrincipal.ventanaProveedores();
    }
    
}
