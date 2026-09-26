package org.example.View;

import org.example.Controller.AcademiaController;
import org.example.app.*;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class AcademiaView {
    private final AcademiaController controller;
    private final BorderPane root = new BorderPane();
    private final TableView<Estudiante> tabla = new TableView<>();
    private final TableView<ProgramaAcademico> tablaProgramas = new TableView<>();
    private final TableView<Docente> tablaDocentes = new TableView<>();
    private final TableView<Matricula> tablaMatriculas = new TableView<>();
    private final Label estado = new Label("Todo listo para gestionar tu academia.");
    private final NumberFormat moneda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"));

    public AcademiaView(AcademiaController controller) { this.controller = controller; construir(); }
    public Parent getRoot() { return root; }

    private void construir() {
        root.getStyleClass().add("root-app");
        VBox sidebar = new VBox(18); sidebar.getStyleClass().add("sidebar"); sidebar.setPadding(new Insets(28, 18, 24, 18)); sidebar.setPrefWidth(230);
        Label logo = new Label("✦  LinguaPlus"); logo.getStyleClass().add("logo");
        Label small = new Label("GESTIÓN ACADÉMICA"); small.getStyleClass().add("eyebrow");
        Button inicio = nav("⌂   Resumen"), estudiantes = nav("♙   Estudiantes"), docentes = nav("♧   Docentes"), programas = nav("◎   Programas"), matriculas = nav("▤   Matrículas"), consultas = nav("⌕   Consultas");
        Region spacer = new Region(); VBox.setVgrow(spacer, Priority.ALWAYS);
        Label profile = new Label("●   Administración\n      Academia LinguaPlus"); profile.getStyleClass().add("profile");
        sidebar.getChildren().addAll(logo, small, inicio, estudiantes, docentes, programas, matriculas, consultas, spacer, profile); root.setLeft(sidebar);
        root.setCenter(resumen()); actualizarTabla();
        inicio.setOnAction(e -> root.setCenter(resumen())); estudiantes.setOnAction(e -> root.setCenter(panelEstudiantes()));
        programas.setOnAction(e -> root.setCenter(panelProgramas())); matriculas.setOnAction(e -> root.setCenter(panelMatriculas()));
        docentes.setOnAction(e -> root.setCenter(panelDocentes()));
        consultas.setOnAction(e -> root.setCenter(panelConsultas()));
        estado.getStyleClass().add("status"); root.setBottom(estado);
    }

    private VBox resumen() {
        VBox page = page(); page.getChildren().addAll(header("Bienvenidos", "Administra estudiantes, programas y matrículas desde un solo lugar."));
        HBox cards = new HBox(14, metric("Estudiantes", String.valueOf(controller.estudiantes().size()), "Inscritos en la academia", "♙"), metric("Matrículas", String.valueOf(controller.matriculas().size()), "Registros realizados", "▤"), metric("Programas", String.valueOf(controller.getAcademia().getProgramas().size()), "Oferta académica", "◎"));
        VBox welcome = new VBox(10); welcome.getStyleClass().add("welcome-card");
        Label title = new Label("Aprender idiomas abre nuevos caminos."); title.getStyleClass().add("welcome-title");
        Label text = new Label("LinguaPlus · Tu espacio para acompañar cada proceso de aprendizaje."); text.getStyleClass().add("muted");
        Button go = new Button("Gestionar estudiantes  →"); go.getStyleClass().add("primary"); go.setOnAction(e -> root.setCenter(panelEstudiantes()));
        welcome.getChildren().addAll(new Label("✧  LINGUAPLUS"), title, text, go);
        Label latest = sectionTitle("Estudiantes recientes"); tabla.setPrefHeight(250);
        page.getChildren().addAll(cards, welcome, latest, tabla); return page;
    }

    private VBox panelEstudiantes() {
        VBox page = page(); page.getChildren().add(header("Estudiantes", "Consulta y registra a las personas que hacen parte de LinguaPlus."));
        Button add = new Button("＋  Nuevo estudiante"); add.getStyleClass().add("primary"); add.setOnAction(e -> dialogoEstudiante());
        HBox row = new HBox(add); row.setAlignment(Pos.CENTER_RIGHT);
        tabla.setPrefHeight(410); page.getChildren().addAll(row, tabla); return page;
    }

    private VBox panelDocentes() {
        VBox page = page(); page.getChildren().add(header("Docentes", "Registra y consulta docentes y sus idiomas de especialidad."));
        Button add = new Button("＋  Nuevo docente"); add.getStyleClass().add("primary"); add.setOnAction(e -> dialogoDocente());
        HBox row = new HBox(add); row.setAlignment(Pos.CENTER_RIGHT);
        if (tablaDocentes.getColumns().isEmpty()) {
            tablaDocentes.getColumns().add(colDocente("Documento", Docente::getNumeroId, 130));
            tablaDocentes.getColumns().add(colDocente("Nombre", Docente::getNombre, 220));
            tablaDocentes.getColumns().add(colDocente("Teléfono", Docente::getTelefono, 150));
            tablaDocentes.getColumns().add(colDocente("Especialidad", d -> idiomaLegible(d.getIdiomaEspecialidad()), 160));
            tablaDocentes.getColumns().add(colDocente("Tarifa por sesión", d -> moneda.format(d.getTarifaSesion()), 170));
            tablaDocentes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); tablaDocentes.getStyleClass().add("data-table");
        }
        tablaDocentes.setItems(FXCollections.observableArrayList(controller.docentes())); tablaDocentes.setPrefHeight(420);
        page.getChildren().addAll(row, tablaDocentes); return page;
    }

    private void dialogoDocente() {
        Dialog<ButtonType> dialog = new Dialog<>(); dialog.setTitle("Nuevo docente"); dialog.setHeaderText("Completa la información del docente.");
        ButtonType save = new ButtonType("Guardar docente", ButtonBar.ButtonData.OK_DONE); dialog.getDialogPane().getButtonTypes().addAll(save, ButtonType.CANCEL);
        TextField documento = field("Documento"), nombre = field("Nombre completo"), telefono = field("Teléfono"), tarifa = field("Valor por sesión");
        ComboBox<IdiomaEspecialidad> especialidad = new ComboBox<>(FXCollections.observableArrayList(IdiomaEspecialidad.values()));
        especialidad.setConverter(new javafx.util.StringConverter<>() { public String toString(IdiomaEspecialidad i) { return i == null ? "" : idiomaLegible(i); } public IdiomaEspecialidad fromString(String s) { return null; } });
        especialidad.getSelectionModel().selectFirst(); especialidad.setMaxWidth(Double.MAX_VALUE);
        GridPane form = new GridPane(); form.setHgap(12); form.setVgap(12);
        form.addRow(0, new Label("Documento"), documento); form.addRow(1, new Label("Nombre"), nombre); form.addRow(2, new Label("Teléfono"), telefono);
        form.addRow(3, new Label("Idioma de especialidad"), especialidad); form.addRow(4, new Label("Tarifa por sesión"), tarifa); dialog.getDialogPane().setContent(form);
        dialog.showAndWait().filter(save::equals).ifPresent(button -> { try {
            controller.registrarDocente(documento.getText(), nombre.getText(), telefono.getText(), especialidad.getValue(), Double.parseDouble(tarifa.getText()));
            root.setCenter(panelDocentes()); estado.setText("Docente registrado correctamente.");
        } catch (Exception ex) { alerta(ex.getMessage() == null ? "Revisa el valor de la tarifa." : ex.getMessage()); } });
    }

    private VBox panelProgramas() {
        VBox page = page(); page.getChildren().add(header("Programas académicos", "Crea y consulta la oferta de cursos de idiomas."));
        Button add = new Button("＋  Crear programa"); add.getStyleClass().add("primary"); add.setOnAction(e -> dialogoPrograma());
        HBox row = new HBox(add); row.setAlignment(Pos.CENTER_RIGHT);
        actualizarTablaProgramas(); tablaProgramas.setPrefHeight(420); page.getChildren().addAll(row, tablaProgramas); return page;
    }

    private VBox panelMatriculas() {
        VBox page = page(); page.getChildren().add(header("Matrículas", "Registra estudiantes en programas con fecha de inicio y descuento."));
        Button add = new Button("＋  Crear matrícula"); add.getStyleClass().add("primary"); add.setOnAction(e -> dialogoMatricula());
        HBox row = new HBox(add); row.setAlignment(Pos.CENTER_RIGHT);
        actualizarTablaMatriculas(); tablaMatriculas.setPrefHeight(420); page.getChildren().addAll(row, tablaMatriculas); return page;
    }

    private VBox panelConsultas() {
        VBox page = page(); page.getChildren().add(header("Consultas", "Encuentra estudiantes y revisa indicadores de la academia."));
        VBox phoneCard = card(); Label ph = sectionTitle("Buscar por teléfono"); TextField phone = new TextField(); phone.setPromptText("Ej. 3001234567");
        Label result = new Label("Escribe un número para consultar."); result.getStyleClass().add("muted");
        Button search = new Button("Buscar estudiante"); search.getStyleClass().add("primary"); search.setOnAction(e -> { String found = controller.consultarTelefono(phone.getText()); boolean perfect = controller.telefonoEsPerfecto(phone.getText()); result.setText(found + "\n" + (perfect ? "El número es perfecto ✦" : "El número no es perfecto")); });
        phoneCard.getChildren().addAll(ph, phone, search, result);
        VBox income = card(); Label in = sectionTitle("Ingresos generados entre fechas"); DatePicker desde = new DatePicker(LocalDate.now().withDayOfMonth(1)), hasta = new DatePicker(LocalDate.now());
        VBox desdeBox = new VBox(6, new Label("Desde"), desde), hastaBox = new VBox(6, new Label("Hasta"), hasta);
        HBox dates = new HBox(12, desdeBox, hastaBox); Label total = new Label("Elige las fechas y calcula el total generado."); total.getStyleClass().add("metric-value");
        Button calculate = new Button("Generar valor final"); calculate.getStyleClass().add("secondary"); calculate.setOnAction(e -> { try { total.setText("Total generado: " + moneda.format(controller.ingresos(desde.getValue(), hasta.getValue()))); } catch (Exception ex) { alerta(ex.getMessage()); } });
        income.getChildren().addAll(in, new Label("Se suman las matrículas cuya fecha de inicio esté dentro del rango."), dates, calculate, total);
        page.getChildren().addAll(phoneCard, income); return page;
    }

    private void dialogoPrograma() {
        Dialog<ButtonType> dialog = new Dialog<>(); dialog.setTitle("Crear programa académico"); dialog.setHeaderText("Registra un curso para la oferta de LinguaPlus.");
        ButtonType save = new ButtonType("Guardar programa", ButtonBar.ButtonData.OK_DONE); dialog.getDialogPane().getButtonTypes().addAll(save, ButtonType.CANCEL);
        TextField codigo = field("Ej. ING-INT"), nombre = field("Nombre del programa"), idioma = field("Idioma"), descripcion = field("Descripción"), meses = field("Duración en meses"), valor = field("Valor mensual"), beneficio = field("Beneficios");
        ComboBox<String> tipo = new ComboBox<>(FXCollections.observableArrayList("Básico", "Intensivo", "Personalizado")); tipo.getSelectionModel().selectFirst(); tipo.setMaxWidth(Double.MAX_VALUE);
        GridPane form = new GridPane(); form.setHgap(12); form.setVgap(10);
        form.addRow(0, new Label("Código"), codigo); form.addRow(1, new Label("Nombre"), nombre); form.addRow(2, new Label("Idioma"), idioma);
        form.addRow(3, new Label("Descripción"), descripcion); form.addRow(4, new Label("Duración (meses)"), meses); form.addRow(5, new Label("Valor mensual"), valor);
        form.addRow(6, new Label("Tipo"), tipo); form.addRow(7, new Label("Beneficios"), beneficio); dialog.getDialogPane().setContent(form);
        dialog.showAndWait().filter(save::equals).ifPresent(button -> { try {
            controller.registrarPrograma(codigo.getText(), nombre.getText(), idioma.getText(), descripcion.getText(), Integer.parseInt(meses.getText()), Double.parseDouble(valor.getText()), tipo.getValue(), beneficio.getText());
            actualizarTablaProgramas(); root.setCenter(panelProgramas()); estado.setText("Programa creado y agregado a la oferta académica.");
        } catch (Exception ex) { alerta(ex.getMessage() == null ? "Revisa la duración y el valor mensual." : ex.getMessage()); } });
    }

    private void dialogoMatricula() {
        if (controller.estudiantes().isEmpty() || controller.programas().isEmpty() || controller.periodos().isEmpty()) { alerta("Para crear una matrícula necesitas al menos un estudiante, un programa y un periodo académico."); return; }
        if (controller.programas().stream().noneMatch(p -> p.getEstado() == EstadoProgra.ACTIVO)) { alerta("No hay programas activos disponibles para matricular."); return; }
        Dialog<ButtonType> dialog = new Dialog<>(); dialog.setTitle("Crear matrícula"); dialog.setHeaderText("Asigna un programa y una fecha de inicio al estudiante.");
        ButtonType save = new ButtonType("Registrar matrícula", ButtonBar.ButtonData.OK_DONE); dialog.getDialogPane().getButtonTypes().addAll(save, ButtonType.CANCEL);
        ComboBox<Estudiante> estudiante = new ComboBox<>(FXCollections.observableArrayList(controller.estudiantes()));
        estudiante.setConverter(new javafx.util.StringConverter<>() { public String toString(Estudiante e) { return e == null ? "" : e.getNombre() + " · " + e.getNumeroId(); } public Estudiante fromString(String s) { return null; } }); estudiante.setMaxWidth(Double.MAX_VALUE);
        ComboBox<ProgramaAcademico> programa = new ComboBox<>(FXCollections.observableArrayList(controller.programas().stream().filter(p -> p.getEstado() == EstadoProgra.ACTIVO).toList()));
        programa.setConverter(new javafx.util.StringConverter<>() { public String toString(ProgramaAcademico p) { return p == null ? "" : p.getNombre() + " · " + p.getCodigo(); } public ProgramaAcademico fromString(String s) { return null; } }); programa.setMaxWidth(Double.MAX_VALUE);
        ComboBox<PeriodoAcademico> periodo = new ComboBox<>(FXCollections.observableArrayList(controller.periodos()));
        periodo.setConverter(new javafx.util.StringConverter<>() { public String toString(PeriodoAcademico p) { return p == null ? "" : p.getCodigo() + " · cupos: " + p.getCuposDisponibles(); } public PeriodoAcademico fromString(String s) { return null; } }); periodo.setMaxWidth(Double.MAX_VALUE);
        if (!estudiante.getItems().isEmpty()) estudiante.getSelectionModel().selectFirst(); if (!programa.getItems().isEmpty()) programa.getSelectionModel().selectFirst();
        if (!periodo.getItems().isEmpty()) periodo.getSelectionModel().selectFirst();
        ComboBox<Docente> docente = new ComboBox<>(FXCollections.observableArrayList(controller.docentes()));
        docente.setPromptText(controller.docentes().isEmpty() ? "Registra docentes desde el menú Docentes" : "Opcional: asignar docente");
        docente.setConverter(new javafx.util.StringConverter<>() { public String toString(Docente d) { return d == null ? "" : d.getNombre() + " · " + d.getNumeroId() + " · " + idiomaLegible(d.getIdiomaEspecialidad()); } public Docente fromString(String s) { return null; } }); docente.setMaxWidth(Double.MAX_VALUE);
        ComboBox<ModalidadPrograma> modalidad = new ComboBox<>(FXCollections.observableArrayList(ModalidadPrograma.values()));
        modalidad.setConverter(new javafx.util.StringConverter<>() { public String toString(ModalidadPrograma m) { return m == null ? "" : m == ModalidadPrograma.PRESENCIAL ? "Presencial · material impreso y carné físico" : "Virtual · licencia y carné digital"; } public ModalidadPrograma fromString(String s) { return null; } }); modalidad.getSelectionModel().selectFirst(); modalidad.setMaxWidth(Double.MAX_VALUE);
        DatePicker inicio = new DatePicker(LocalDate.now()); Spinner<Integer> descuento = new Spinner<>(0, 30, 0); descuento.setEditable(true);
        TextArea observacion = new TextArea(); observacion.setPromptText("Observaciones (opcional)"); observacion.setPrefRowCount(2);
        GridPane form = new GridPane(); form.setHgap(12); form.setVgap(10); form.addRow(0, new Label("Estudiante"), estudiante); form.addRow(1, new Label("Programa"), programa);
        form.addRow(2, new Label("Periodo"), periodo); form.addRow(3, new Label("Docente"), docente); form.addRow(4, new Label("Modalidad"), modalidad); form.addRow(5, new Label("Fecha de inicio"), inicio); form.addRow(6, new Label("Descuento (%)"), descuento); form.addRow(7, new Label("Observaciones"), observacion);
        dialog.getDialogPane().setContent(form);
        dialog.showAndWait().filter(save::equals).ifPresent(button -> { try {
            Matricula creada = controller.crearMatricula(estudiante.getValue().getNumeroId(), programa.getValue().getCodigo(), periodo.getValue().getCodigo(), docente.getValue() == null ? null : docente.getValue().getNumeroId(), inicio.getValue(), descuento.getValue(), observacion.getText(), modalidad.getValue());
            actualizarTablaMatriculas(); root.setCenter(panelMatriculas()); estado.setText("Matrícula " + creada.getNumeroMatricula() + " registrada correctamente.");
            new Alert(Alert.AlertType.INFORMATION, "Matrícula N.º " + creada.getNumeroMatricula() + "\nValor inicial: " + moneda.format(creada.getValorInicial()) + "\nDescuento (" + (int)(creada.getDescuento() * 100) + "%): −" + moneda.format(creada.getValorDescontado()) + "\nValor final: " + moneda.format(creada.getValorTotal()), ButtonType.OK).showAndWait();
        } catch (Exception ex) { alerta(ex.getMessage()); } });
    }

    private void dialogoEstudiante() {
        Dialog<ButtonType> dialog = new Dialog<>(); dialog.setTitle("Nuevo estudiante"); dialog.setHeaderText("Completa los datos básicos del estudiante.");
        ButtonType save = new ButtonType("Guardar estudiante", ButtonBar.ButtonData.OK_DONE); dialog.getDialogPane().getButtonTypes().addAll(save, ButtonType.CANCEL);
        TextField documento = field("Documento"), nombre = field("Nombre completo"), telefono = field("Teléfono"), correo = field("Correo electrónico"), edad = field("Edad");
        GridPane form = new GridPane(); form.setHgap(12); form.setVgap(12); form.addRow(0, new Label("Documento"), documento); form.addRow(1, new Label("Nombre"), nombre); form.addRow(2, new Label("Teléfono"), telefono); form.addRow(3, new Label("Correo"), correo); form.addRow(4, new Label("Edad"), edad);
        dialog.getDialogPane().setContent(form);
        dialog.showAndWait().filter(save::equals).ifPresent(type -> { try { controller.registrarEstudiante(documento.getText(), nombre.getText(), telefono.getText(), correo.getText(), Integer.parseInt(edad.getText())); actualizarTabla(); root.setCenter(panelEstudiantes()); estado.setText("Estudiante registrado correctamente."); } catch (Exception ex) { alerta(ex.getMessage() == null ? "Revisa el campo edad." : ex.getMessage()); } });
    }

    private void actualizarTabla() {
        if (tabla.getColumns().isEmpty()) {
            tabla.getColumns().add(col("Documento", e -> e.getNumeroId(), 130)); tabla.getColumns().add(col("Nombre", e -> e.getNombre(), 220)); tabla.getColumns().add(col("Edad", Estudiante::getEdad, 65)); tabla.getColumns().add(col("Teléfono", e -> e.getTelefono(), 140)); tabla.getColumns().add(col("Correo electrónico", Estudiante::getCorreoElectronico, 260)); tabla.getColumns().add(col("Registro", e -> String.valueOf(e.getFechaRegistro()), 130));
            tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); tabla.getStyleClass().add("data-table");
        }
        tabla.setItems(FXCollections.observableArrayList(controller.estudiantes()));
    }
    private void actualizarTablaProgramas() {
        if (tablaProgramas.getColumns().isEmpty()) {
            tablaProgramas.getColumns().add(colPrograma("Código", ProgramaAcademico::getCodigo, 110)); tablaProgramas.getColumns().add(colPrograma("Programa", ProgramaAcademico::getNombre, 220));
            tablaProgramas.getColumns().add(colPrograma("Idioma", ProgramaAcademico::getIdioma, 120)); tablaProgramas.getColumns().add(colPrograma("Duración", p -> p.getDuracionMeses() + " meses", 110));
            tablaProgramas.getColumns().add(colPrograma("Valor mensual", p -> moneda.format(p.getValorMensual()), 150)); tablaProgramas.getColumns().add(colPrograma("Estado", p -> p.getEstado().toString(), 110));
            tablaProgramas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); tablaProgramas.getStyleClass().add("data-table");
        }
        tablaProgramas.setItems(FXCollections.observableArrayList(controller.programas()));
    }
    private void actualizarTablaMatriculas() {
        if (tablaMatriculas.getColumns().isEmpty()) {
            tablaMatriculas.getColumns().add(colMatricula("N.º", m -> String.valueOf(m.getNumeroMatricula()), 70)); tablaMatriculas.getColumns().add(colMatricula("Estudiante", m -> m.getEstudiante().getNombre(), 200));
            tablaMatriculas.getColumns().add(colMatricula("Programa", m -> m.getProgramasAcademicos().get(0).getNombre(), 180)); tablaMatriculas.getColumns().add(colMatricula("Fecha de inicio", m -> m.getFechaInicio().toString(), 130));
            tablaMatriculas.getColumns().add(colMatricula("Docente", m -> m.getDocenteTutor() == null ? "Sin asignar" : m.getDocenteTutor().getNombre(), 170));
            tablaMatriculas.getColumns().add(colMatricula("Valor inicial", m -> moneda.format(m.getValorInicial()), 145));
            tablaMatriculas.getColumns().add(colMatricula("Descuento (%)", m -> String.format(Locale.forLanguageTag("es-CO"), "%.0f%%", m.getDescuento() * 100), 110));
            tablaMatriculas.getColumns().add(colMatricula("Descuento", m -> "−" + moneda.format(m.getValorDescontado()), 145));
            tablaMatriculas.getColumns().add(colMatricula("Valor final", m -> moneda.format(m.getValorTotal()), 145)); tablaMatriculas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); tablaMatriculas.getStyleClass().add("data-table");
        }
        tablaMatriculas.setItems(FXCollections.observableArrayList(controller.matriculas()));
    }
    private <T> TableColumn<ProgramaAcademico,T> colPrograma(String title, java.util.function.Function<ProgramaAcademico,T> f, int width) { TableColumn<ProgramaAcademico,T> c = new TableColumn<>(title); c.setCellValueFactory(x -> new javafx.beans.property.ReadOnlyObjectWrapper<>(f.apply(x.getValue()))); c.setPrefWidth(width); return c; }
    private <T> TableColumn<Matricula,T> colMatricula(String title, java.util.function.Function<Matricula,T> f, int width) { TableColumn<Matricula,T> c = new TableColumn<>(title); c.setCellValueFactory(x -> new javafx.beans.property.ReadOnlyObjectWrapper<>(f.apply(x.getValue()))); c.setPrefWidth(width); return c; }
    private <T> TableColumn<Docente,T> colDocente(String title, java.util.function.Function<Docente,T> f, int width) { TableColumn<Docente,T> c = new TableColumn<>(title); c.setCellValueFactory(x -> new javafx.beans.property.ReadOnlyObjectWrapper<>(f.apply(x.getValue()))); c.setPrefWidth(width); return c; }
    private String idiomaLegible(IdiomaEspecialidad idioma) { return idioma.name().charAt(0) + idioma.name().substring(1).toLowerCase(Locale.ROOT); }
    private <T> TableColumn<Estudiante,T> col(String title, java.util.function.Function<Estudiante,T> f, int width) { TableColumn<Estudiante,T> c = new TableColumn<>(title); c.setCellValueFactory(x -> new javafx.beans.property.ReadOnlyObjectWrapper<>(f.apply(x.getValue()))); c.setPrefWidth(width); return c; }
    private VBox page() { VBox v = new VBox(22); v.setPadding(new Insets(34, 38, 30, 38)); return v; }
    private VBox header(String title, String subtitle) { VBox b = new VBox(6); Label t = new Label(title); t.getStyleClass().add("page-title"); Label s = new Label(subtitle); s.getStyleClass().add("muted"); b.getChildren().addAll(t,s); return b; }
    private VBox metric(String title, String value, String subtitle, String icon) { VBox b = card(); HBox h = new HBox(new Label(title), new Region(), new Label(icon)); HBox.setHgrow(h.getChildren().get(1), Priority.ALWAYS); Label val = new Label(value); val.getStyleClass().add("metric-value"); Label sub = new Label(subtitle); sub.getStyleClass().add("muted"); b.getChildren().addAll(h,val,sub); b.setPrefWidth(220); return b; }
    private VBox card() { VBox b = new VBox(14); b.getStyleClass().add("card"); b.setPadding(new Insets(20)); return b; }
    private Label sectionTitle(String text) { Label l = new Label(text); l.getStyleClass().add("section-title"); return l; }
    private Button nav(String text) { Button b = new Button(text); b.getStyleClass().add("nav-button"); b.setMaxWidth(Double.MAX_VALUE); b.setAlignment(Pos.CENTER_LEFT); return b; }
    private TextField field(String hint) { TextField f = new TextField(); f.setPromptText(hint); return f; }
    private void alerta(String message) { new Alert(Alert.AlertType.WARNING, message, ButtonType.OK).showAndWait(); }
}
