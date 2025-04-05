package window;

//import java.nio.file.Path;
import src.*;
import testfiles.Matrice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.scene.shape.*;
import javafx.scene.input.DragEvent;
import javafx.event.EventHandler;
import javafx.scene.input.TransferMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.Cursor;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.ScrollEvent;
import javafx.geometry.BoundingBox;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.geometry.Bounds;

import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D;
import java.lang.Math;

public class Whiteboard {
    /*
     * private enum State
     * {
     * DRAGGING_ELEM,
     * }
     */

    private Pane panel;
    private HashMap<Integer, GraphicElem> circuitComponents;
    public Circuit circuit;
    private GraphicElem elemToAdd;
    private GraphicWire wireToAdd;
    public boolean cursorOverHere = false; // cursor is on the whiteBoard
    private boolean cursorSelectionMode = true; // cursor is in selection mode or either hand mod
    private boolean drawingWire = false;
    // private double zoomMultiplicator = 1.025;
    private double scaleFactor = 1;
    private double initMousePos[]; // initial mouse position when starting mouse dragging
    private double transValue[] = null; // accumlated translation value of the scene
    private BoundingBox selectionBounds = null; // boudingBox of the the selection currently being made
    private Rectangle selectionRec = null;
    private Rectangle selectedElemRec = null; // rectangle containing all selected elements
    private boolean selectedElemTranslationActive = false;
    private double[] selectedElemRecTransValue = null;
    private HashMap<Integer, double[]> selectedElemTransValue;
    private ArrayList<Integer> selectedElemsId;
    private ArrayList<ArrayList<Integer>> grid; // 1 for something present, 0 otherwise
    private int gridLineNb = 0;
    private int gridColNb = 0;
    private double gridRatio = 40.0; // double unit*gridRatio = height/width of a cell of the grid
    private boolean wireDragging = false;
    private boolean wireStartIsOutput = false;
    private final static double backgroundDotRadius = 0.05;

    public Whiteboard() {
        panel = new Pane();
        circuitComponents = new HashMap<>();
        selectedElemsId = new ArrayList<>();
        selectedElemTransValue = new HashMap<>();
        transValue = new double[] { 0.0, 0.0 };
        selectedElemRecTransValue = new double[2];
        grid = new ArrayList<>();

        panel.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                dragOverComponentHandler(event);
                event.consume();
            }
        });

        panel.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                panel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                event.consume();
            }
        });

        panel.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                panel.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
                add(elemToAdd, event.getX(), event.getY());
                event.setDropCompleted(elemToAdd.getShape() != null);
                event.consume();
            }
        });

        panel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!cursorSelectionMode)
                    App.setCursor(Cursor.OPEN_HAND);
                cursorOverHere = true;
                event.consume();
            }

        });

        panel.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePressedHandler(event);
                event.consume();
            }
        });

        panel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseDraggedHandler(event);
                event.consume();
            }
        });

        panel.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseReleasedHandler(event);
                event.consume();
            }
        });

        panel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                App.setCursor(Cursor.DEFAULT);
                cursorOverHere = false;
                event.consume();
            }
        });

        panel.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                for (Node elem : panel.getChildren()) {
                    /*
                     * if(event.getDeltaY() < 0)
                     * scaleFactor +=
                     * 1/(event.getDeltaY()/event.getMultiplierY()*zoomMultiplicator);
                     * else
                     * scaleFactor += event.getDeltaY()/event.getMultiplierY()*zoomMultiplicator;
                     * 
                     * elem.setScaleX(scaleFactor);
                     * elem.setScaleY(scaleFactor);
                     * 
                     * System.out.println( "event.getDeltaY() : " + event.getDeltaY() +
                     * "event.getMultiplierY() : " + event.getMultiplierY());
                     * System.out.println( "scaleFactor : " + scaleFactor);
                     */
                }
            }
        });

    }

    public Whiteboard(HashMap<Integer, GraphicElem> circuit) {
        this();
        // not finished
    }

    /**
     * Toggles the cursor mode between selection mode and hand mode.
     * 
     * - In selection mode:
     * - The cursor is set to the default pointer.
     * - Nodes in the panel can be selected or interacted with.
     * 
     * - In hand mode:
     * - The cursor is set to an open hand icon.
     * - The user can pan around the whiteboard.
     */
    public void changeCursorSelectionMode() {
        if (cursorSelectionMode) {
            cursorSelectionMode = false;
            App.setCursor(Cursor.OPEN_HAND);
            for (Node elem : panel.getChildren()) {
                // elem.getStyleClass().clear();
                // elem.getStyleClass().add("jsp");
            }
        }

        else {
            cursorSelectionMode = true;
            App.setCursor(Cursor.DEFAULT);
            for (Node elem : panel.getChildren()) {
                // elem.getStyleClass().add("whiteBoardElem");
            }
        }
    }

    /**
     * Adjusts the grid size by modifying the number of rows and columns.
     * 
     * - If the new grid size is smaller, rows or columns are removed.
     * - If the new grid size is larger, rows or columns are added and initialized
     * to 0.
     * - Updates the internal grid representation and ensures consistency.
     * 
     * @param lineNb The desired number of rows in the grid.
     * @param colNb  The desired number of columns in the grid.
     */
    public void setGridSize(int lineNb, int colNb) {
        if (lineNb < gridLineNb) {
            for (int i = gridLineNb - 1; i > lineNb; --i) {
                grid.remove(i);
            }
            grid.trimToSize();
        }

        if (colNb < gridColNb) {
            for (int i = 0; i < gridLineNb; ++i) {
                for (int j = gridColNb - 1; j > colNb; --j) {

                    grid.get(i).remove(j);
                }
                grid.get(i).trimToSize();
            }
        }

        if (lineNb > gridLineNb) {
            grid.ensureCapacity(lineNb);
            for (int line = 0; line < lineNb - gridLineNb; ++line) {
                grid.add(new ArrayList<>());

                grid.get(line + gridLineNb).ensureCapacity(gridColNb);

                for (int col = 0; col < gridColNb; ++col) {
                    grid.get(line + gridLineNb).add(0);
                }
            }
            setGrid();
        }

        if (colNb > gridColNb) {
            for (int line = 0; line < gridLineNb; ++line) {
                grid.get(line).ensureCapacity(colNb);

                for (int col = 0; col < colNb - gridColNb; ++col) {
                    grid.get(line).add(0);
                }
            }
            setGrid();
        }

        gridLineNb = lineNb;
        gridColNb = colNb;
        // new Matrice(grid, lineNb, colNb);
    }

    /**
     * Updates the grid to reflect the positions of the circuit components.
     * 
     * - Iterates through all circuit components and calculates their grid coverage
     * based on their bounds and the grid ratio.
     * - Marks the grid cells occupied by each component with a value of 1.
     * - Ensures that components outside the panel bounds are ignored.
     */
    private void setGrid() {

        for (GraphicElem elem : circuitComponents.values()) {
            Bounds elemBounds = elem.getShape().getBoundsInParent();

            if (elemBounds.getMinX() > panel.getBoundsInLocal().getMaxX()
                    ||
                    elemBounds.getMaxX() < 0
                    ||
                    elemBounds.getMinY() > panel.getBoundsInLocal().getMaxY()
                    ||
                    elemBounds.getMaxY() < 0)
                continue;

            int[] gridStart = new int[2];
            int[] gridEnd = new int[2];

            gridStart[0] = ((int) (elem.getShape().getBoundsInParent().getMinY() / gridRatio)) - 1;
            gridStart[1] = ((int) (elem.getShape().getBoundsInParent().getMinX() / gridRatio)) - 1;
            gridEnd[0] = gridStart[0] + ((int) (elem.getShape().getBoundsInParent().getHeight() / gridRatio)) - 1;
            gridEnd[1] = gridStart[1] + ((int) (elem.getShape().getBoundsInParent().getWidth() / gridRatio)) - 1;

            gridStart[0] = (gridStart[0] < 0) ? 0 : gridStart[0];
            gridStart[1] = (gridStart[1] < 0) ? 0 : gridStart[1];
            gridStart[0] = (gridStart[0] > gridLineNb) ? gridLineNb : gridStart[0];
            gridStart[1] = (gridStart[1] > gridColNb) ? gridColNb : gridStart[1];
            gridEnd[0] = (gridEnd[0] < 0) ? 0 : gridEnd[0];
            gridEnd[1] = (gridEnd[1] < 0) ? 0 : gridEnd[1];
            gridEnd[0] = (gridEnd[0] > gridLineNb) ? gridLineNb : gridEnd[0];
            gridEnd[1] = (gridEnd[1] > gridColNb) ? gridColNb : gridEnd[1];

            for (int line = gridStart[0]; line <= gridEnd[0]; ++line) {
                for (int col = gridStart[1]; col <= gridEnd[1]; ++col) {
                    System.out.println("[-1-]");
                    grid.get(line).set(col, 1);
                }
            }
        }
    }

    /**
     * Sets the background of the panel with a grid of dots.
     * 
     * - Creates a grid of circles (dots) based on the panel's dimensions and the
     * specified grid ratio.
     */
    public void setBackground() {
        int i = 0;

        for (int line = 1; line <= panel.getBoundsInLocal().getMaxX() / gridRatio; ++line) {
            ArrayList<Circle> background = new ArrayList<>();
            for (int col = 1; col <= panel.getBoundsInLocal().getMaxY() / gridRatio; ++col) {
                Circle circle = new Circle(line * gridRatio, col * gridRatio, backgroundDotRadius);
                background.add(circle);
                circle.getStyleClass().add("whiteBoardBackgroundDot");
                circle.toBack();

            }
            panel.getChildren().addAll(background);
        }

        /*
         * SVGPath background = new SVGPath();
         * String svgContent = new String();
         * 
         * for (int line=1; line <= panel.getBoundsInLocal().getMaxX() / gridRatio;
         * ++line)
         * {
         * for (int col=1; col <= panel.getBoundsInLocal().getMaxY() / gridRatio; ++col)
         * {
         * svgContent+= circleSvg(new double[]{line*gridRatio,col*gridRatio});
         * }
         * }
         * 
         * background.setContent(svgContent);
         * background.getStyleClass().add("whiteBoardBackgroundDot");
         * panel.getChildren().addAll(background);
         */

    }

    /**
     * Generates an SVG path string for a circle.
     * 
     * - The circle is defined by its center position and radius.
     * - The SVG path uses the "Move To" (M) and "Arc" (A) commands to draw the
     * circle.
     * 
     * @param centerPos A double array containing the x and y coordinates of the
     *                  circle's center.
     * @return A string representing the SVG path for the circle.
     */
    private String circleSvg(double[] centerPos) {
        String res1 = "M" + centerPos[0] + ',' + (centerPos[1] + backgroundDotRadius) + " A" + backgroundDotRadius + ","
                + backgroundDotRadius;
        String res2 = 4.4847698 * backgroundDotRadius + "," + 4.4847698 * backgroundDotRadius;

        return res1 + "0 0,0" + res2 + res1 + "0 1,0" + res2;

        /*
         * String res;
         * res+="M"+centerPos[0]+','+(centerPos[1]+backgroundDotRadius)+" A"
         * +backgroundDotRadius+","+backgroundDotRadius+"0 0,0"
         * +Math.cos(Math.PI/2)*backgroundDotRadius+","+Math.sin(Math.PI/2)*
         * backgroundDotRadius
         * 
         * +"M"+centerPos[0]+','+(centerPos[1]+backgroundDotRadius)+" A"
         * +backgroundDotRadius+","+backgroundDotRadius+"0 1,0"
         * +Math.cos(Math.PI/2)*backgroundDotRadius+","+Math.sin(Math.PI/2)*
         * backgroundDotRadius ;
         */
    }

    /**
     * Adds a new graphical element to the whiteboard.
     * 
     * - The element is added to the `circuitComponents` map and displayed on the
     * panel.
     * - The element's position, size, and bounds are adjusted based on the grid
     * ratio.
     * - Updates the grid to mark the cells occupied by the element.
     * - Sets up mouse click handlers for the element to display its attributes.
     * 
     * @param elem The graphical element to be added.
     * @param xPos The x-coordinate where the element should be placed.
     * @param yPos The y-coordinate where the element should be placed.
     */
    private void add(GraphicElem elem, double xPos, double yPos) {
        circuitComponents.put(elem.getId(), elem);
        Shape shape = elem.getShape();
        shape.setScaleX(elem.getShape().getBoundsInLocal().getWidth() / gridRatio * elem.getsizeMultiplicator());
        shape.setScaleY(elem.getShape().getBoundsInLocal().getHeight() / gridRatio * elem.getsizeMultiplicator());

        elemToAdd.getShape().relocate(xPos, yPos);
        elem.setBounds(elem.getShape().getBoundsInParent());

        shape.getStyleClass().add("whiteBoardElem");
        panel.getChildren().addAll(shape);

        // shape.setScaleX(scaleFactor);
        // shape.setScaleY(scaleFactor);

        setInOut(elem);

        // add to the grid
        int[] gridStart = new int[2];
        int[] gridEnd = new int[2];

        gridStart[0] = ((int) (shape.getBoundsInParent().getMinY() / gridRatio)) - 1;
        gridStart[1] = ((int) (shape.getBoundsInParent().getMinX() / gridRatio)) - 1;
        gridEnd[0] = gridStart[0] + ((int) (shape.getBoundsInParent().getHeight() / gridRatio)) - 1;
        gridEnd[1] = gridStart[1] + ((int) (shape.getBoundsInParent().getWidth() / gridRatio)) - 1;

        gridStart[0] = (gridStart[0] < 0) ? 0 : gridStart[0];
        gridStart[1] = (gridStart[1] < 0) ? 0 : gridStart[1];
        gridStart[0] = (gridStart[0] > gridLineNb) ? gridLineNb : gridStart[0];
        gridStart[1] = (gridStart[1] > gridColNb) ? gridColNb : gridStart[1];
        gridEnd[0] = (gridEnd[0] < 0) ? 0 : gridEnd[0];
        gridEnd[1] = (gridEnd[1] < 0) ? 0 : gridEnd[1];
        gridEnd[0] = (gridEnd[0] > gridLineNb) ? gridLineNb : gridEnd[0];
        gridEnd[1] = (gridEnd[1] > gridColNb) ? gridColNb : gridEnd[1];

        System.out.println("gridEnd[0] : " + gridEnd[0] + " gridEnd[1] : " + gridEnd[1] + "\n");
        grid.get(gridEnd[0]).set(gridEnd[1], 1);

        for (int line = gridStart[0]; line <= gridEnd[0]; ++line) {
            for (int col = gridStart[1]; col <= gridEnd[1]; ++col) {
                System.out.println("[-1-]");
                grid.get(line).set(col, 1);
            }
        }

        // new Matrice(grid,gridLineNb,gridColNb);

        // System.out.println("\nelem.getBoundsInParent().getMinX() : " +
        // elem.getShape().getBoundsInParent().getMinX()
        // + " elem.getBoundsInParent().getMinY() : " +
        // elem.getShape().getBoundsInParent().getMinY() );

        for (Node ah : panel.getChildren()) {
            if (ah instanceof Circle) {
                // System.out.println("getBoundsInParent().getMinX() : " +
                // ah.getBoundsInParent().getMinX()
                // + " getBoundsInParent().getMinY() : " + ah.getBoundsInParent().getMinY() );
            }
        }

        shape.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // en attendant juste pour test
                if (shape instanceof Rectangle)
                    App.setAttributesPanel(new GraphicElem(new Circuit("project")));

                if (shape instanceof Circle)
                    App.setAttributesPanel(new GraphicElem(new And()));

                if (shape instanceof Polygon)
                    App.setAttributesPanel(new GraphicElem(new Or()));

                event.consume();
            }
        });
    }

    /**
     * Removes all currently selected elements from the whiteboard.
     * 
     * - Removes the graphical elements from the `panel` and the `circuitComponents`
     * map.
     * - Clears the selection rectangle and updates the grid to reflect the changes.
     */
    public void remove() // remove all currenly selected elem
    {
        if (selectedElemsId == null && !selectedElemsId.isEmpty())
            return;

        for (int id : selectedElemsId) {
            GraphicElem elem = circuitComponents.get(id);
            panel.getChildren().removeAll(elem.getShape());

            if (elem.getInputs() != null && !elem.getInputs().isEmpty())
                panel.getChildren().removeAll(elem.getInputs());


            if (elem.getOutputs() != null && !elem.getOutputs().isEmpty())
                panel.getChildren().removeAll(elem.getOutputs());

            if (elem.getInputsLine() != null)
                panel.getChildren().removeAll(elem.getInputsLine());

            circuitComponents.remove(id, elem);
        }

        panel.getChildren().removeAll(selectedElemRec);
        selectedElemsId.clear();
        selectedElemRec = null;
        setGrid();
    }

    /**
     * Configures the input and output connections for a graphical element.
     * 
     * - Calls the `setInOut` method of the provided element to initialize its
     * inputs and outputs.
     * - Adds the input and output graphical representations (e.g., circles) to the
     * panel.
     * - Logs the number of input connections for debugging purposes.
     * 
     * @param elem The graphical element whose inputs and outputs are to be set.
     */
    private void setInOut(GraphicElem elem) {
        elem.setInOut();

        System.out.println("LIST INPUT SIZE : " + elem.getInputs().size());

        panel.getChildren().addAll(elem.getInputs());
        panel.getChildren().addAll(elem.getOutputs());
    }

    /**
     * Handles the drag-over event for graphical elements.
     * 
     * - Checks if the dragged element originates from the `GraphicElemPanel`.
     * - Changes the background color of the whiteboard to indicate a valid drop
     * zone.
     * - Accepts the drag operation if the dragged element has a valid type.
     * - Prepares the corresponding `GraphicElem` based on the type of the dragged
     * element.
     * 
     * @param event The drag event containing information about the dragged element.
     */
    private void dragOverComponentHandler(DragEvent event) // add corresponding elemLogic dragged from graphicElem
    {
        Node source = (Node) event.getGestureSource();
        if ((source.getParent().equals(App.getGraphicElemPanel().getPanel())) && event.getDragboard().hasString()) {
            panel.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
            event.acceptTransferModes(TransferMode.COPY);

            switch (event.getDragboard().getString()) {
                case "circle":
                    elemToAdd = new GraphicElem(new And());
                    break;

                case "rectangle":
                    elemToAdd = new GraphicElem(new Circuit());
                    break;

                default:
                    elemToAdd = new GraphicElem(new Or());
                    break;
            }
        }
    }

    /**
     * Creates a bounding box (rectangle) that visually highlights all selected
     * elements.
     * 
     * - The rectangle is defined by the minimum and maximum positions of the
     * selected elements.
     * - Adds the rectangle to the panel for display.
     * 
     * @param minPos A double array containing the minimum x and y coordinates of
     *               the bounding box.
     * @param maxPos A double array containing the maximum x and y coordinates of
     *               the bounding box.
     */
    private void setSelectedElemRec(double[] minPos, double[] maxPos) // bounding box containing all selected elements
    {
        selectedElemRec = new Rectangle(minPos[0], minPos[1], Math.abs(maxPos[0] - minPos[0]),
                Math.abs(maxPos[1] - minPos[1]));
        selectedElemRec.getStyleClass().add("recSelectedElem");
        panel.getChildren().addAll(selectedElemRec);
    }

    /**
     * Handles the mouse press event on the whiteboard.
     * 
     * - Initializes the mouse position for dragging or selection.
     * - Checks if the mouse press is within the selection rectangle to enable
     * translation.
     * - Handles wire creation if the mouse press is on an input or output circle.
     * - Creates a selection rectangle if in selection mode.
     * 
     * @param event The mouse event containing information about the mouse press.
     */
    private void mousePressedHandler(MouseEvent event) {
        initMousePos = new double[2];
        initMousePos[0] = event.getX();
        initMousePos[1] = event.getY();

        if (selectedElemRec != null) {
            if (initMousePos[0] > selectedElemRec.getX() &&
                    initMousePos[0] < selectedElemRec.getX() + selectedElemRec.getWidth() &&
                    initMousePos[1] > selectedElemRec.getY() &&
                    initMousePos[1] < selectedElemRec.getY() + selectedElemRec.getHeight())
                selectedElemTranslationActive = true;

            else {
                selectedElemTranslationActive = false;
                {
                    panel.getChildren().removeAll(selectedElemRec);
                    selectedElemRec = null;
                    selectedElemsId.clear();
                }
            }
        }

        if (selectionRec!=null) 
        {
            panel.getChildren().removeAll(selectionRec);
            selectionRec = null;
        }

        if (!cursorSelectionMode) {
            App.setCursor(Cursor.CLOSED_HAND);
            System.out.println("Initial mouseXPos: " + initMousePos[0]);
            System.out.println("Initial mouseYPos: " + initMousePos[1]);
        }

        else {
            for (GraphicElem elem : circuitComponents.values()) {

                for (int inputIndex = 0; inputIndex < elem.getInputs().size(); ++inputIndex) {
                    Circle inputCircle = elem.getInputs().get(inputIndex);
                    double radius = inputCircle.getRadius();

                    if (inputCircle.getBoundsInParent().contains(event.getX(), event.getY())) {
                        Point2D start = new Point2D.Double(inputCircle.getCenterX() + radius,
                                inputCircle.getCenterY() + radius);
                        wireToAdd = new GraphicWire(new Wire(start));
                        wireToAdd.getWire().setExit(new src.Pair<>(elem.getElem(), inputIndex));
                        wireDragging = true;
                        wireStartIsOutput = false;
                        return;
                    }
                }

                for (int outpoutIndex = 0; outpoutIndex < elem.getOutputs().size(); ++outpoutIndex) {
                    Circle inputCircle = elem.getOutputs().get(outpoutIndex);
                    double radius = inputCircle.getRadius();

                    if (inputCircle.getBoundsInParent().contains(event.getX(), event.getY())) {
                        Point2D start = new Point2D.Double(inputCircle.getCenterX() + radius,
                                inputCircle.getCenterY() + radius);
                        wireToAdd = new GraphicWire(elem.getElem(), outpoutIndex, start);
                        System.out.println("pos depart fil : " + wireToAdd.getWire().getPosStart());
                        wireDragging = true;
                        wireStartIsOutput = true;
                        return;
                    }
                }
            }

            selectionRec = new Rectangle();
            selectionRec.getStyleClass().add("selectionSquare");
            panel.getChildren().addAll(selectionRec);
        }
    }

    /**
     * Handles the mouse drag event on the whiteboard.
     * 
     * - Updates the position of wires if a wire is being dragged.
     * - Translates all elements in hand mode.
     * - Updates the selection rectangle or translates selected elements in
     * selection mode.
     * 
     * @param event The mouse event containing information about the drag action.
     */
    private void mouseDraggedHandler(MouseEvent event) {
        if (wireDragging) {
            Point2D pos = new Point2D.Double(event.getX(), event.getY());
            wireToAdd.setShape(pos, grid);
            panel.getChildren().removeAll(wireToAdd.getShape());
            panel.getChildren().addAll(wireToAdd.getShape());
            return;
        }

        if (!cursorSelectionMode) // hand mode
        {
            double deltaMousePos[] = new double[2];
            deltaMousePos[0] = event.getX() - initMousePos[0];
            deltaMousePos[1] = event.getY() - initMousePos[1];

            for (Map.Entry<Integer, GraphicElem> entry : circuitComponents.entrySet()) {
                translateElem(entry.getValue(), deltaMousePos);
            }
        }

        else // selection mode
        {
            if (selectedElemTranslationActive) {
                double[] transVec = new double[2];
                transVec[0] = event.getX() - initMousePos[0];
                transVec[1] = event.getY() - initMousePos[1];

                for (int id : selectedElemsId) {
                    if (selectedElemTransValue.get(id) == null) {
                        double[] init = new double[2];
                        init[0] = 0;
                        init[1] = 0;
                        selectedElemTransValue.put(id, init);
                    }

                    translateElem(circuitComponents.get(id), transVec);
                }

                selectedElemRec.setTranslateX(transVec[0] + selectedElemRecTransValue[0]);
                selectedElemRec.setTranslateY(transVec[1] + selectedElemRecTransValue[1]);
            }

            else {
                if (initMousePos == null || selectionRec==null )
                    return;

                double xMin;
                double yMin;

                if (event.getX() < initMousePos[0]) {
                    xMin = event.getX();
                    selectionRec.setX(event.getX());
                }

                else {
                    xMin = initMousePos[0];
                    selectionRec.setX(initMousePos[0]);
                }

                if (event.getY() < initMousePos[1]) {
                    yMin = event.getY();
                    selectionRec.setY(event.getY());
                }

                else {
                    yMin = initMousePos[1];
                    selectionRec.setY(initMousePos[1]);
                }

                selectionBounds = new BoundingBox(xMin, yMin, Math.abs(initMousePos[0] - event.getX()),
                        Math.abs(initMousePos[1] - event.getY()));

                selectionRec.setHeight(selectionBounds.getHeight());
                selectionRec.setWidth(selectionBounds.getWidth());
            }
        }
    }

    /**
     * Handles the mouse release event on the whiteboard.
     * 
     * - Finalizes wire creation if a wire is being dragged.
     * - Updates the translation values for elements in hand mode.
     * - Finalizes the selection rectangle and identifies selected elements in
     * selection mode.
     * 
     * @param event The mouse event containing information about the release action.
     */
    private void mouseReleasedHandler(MouseEvent event) {
        if (wireDragging) {
            for (GraphicElem elem : circuitComponents.values()) {
                for (int inputIndex = 0; inputIndex < elem.getInputs().size(); ++inputIndex) {
                    Circle inputCircle = elem.getInputs().get(inputIndex);

                    if (inputCircle.getBoundsInParent().contains(event.getX(), event.getY())) {
                        Point2D start = new Point2D.Double(event.getX(), event.getY());
                        wireDragging = true;
                        wireToAdd = new GraphicWire(new Wire(start));
                        wireToAdd.getWire().setExit(new src.Pair<>(elem.getElem(), inputIndex));
                        wireStartIsOutput = false;
                        return;
                    }
                }

                for (int outpoutIndex = 0; outpoutIndex < elem.getOutputs().size(); ++outpoutIndex) {
                    Circle inputCircle = elem.getOutputs().get(outpoutIndex);

                    if (inputCircle.getBoundsInParent().contains(event.getX(), event.getY())) {
                        Point2D start = new Point2D.Double(event.getX(), event.getY());
                        wireDragging = true;
                        wireToAdd = new GraphicWire(elem.getElem(), outpoutIndex, start);
                        wireStartIsOutput = true;
                        return;
                    }
                }
            }

            wireDragging = false;
        }

        selectedElemTranslationActive = false;

        if (!cursorSelectionMode) {
            App.setCursor(Cursor.OPEN_HAND);

            transValue[0] += event.getX() - initMousePos[0];
            transValue[1] += event.getY() - initMousePos[1];
        }

        else // selection mode
        {
            if (selectedElemRec != null) {
                for (int id : selectedElemsId) {
                    selectedElemTransValue.get(id)[0] += event.getX() - initMousePos[0];
                    selectedElemTransValue.get(id)[1] += event.getX() - initMousePos[0];
                }
            }

            else {
                panel.getChildren().removeAll(selectionRec);
                selectionRec = null;
                boolean firstLoop = true;

                if (selectionBounds != null) {
                    selectedElemsId.clear();
                    double[] minPos = new double[2];
                    double[] maxPos = new double[2];
                    ArrayList<Shape> circuitComponentsShapes = new ArrayList<>();

                    for (GraphicElem elem : circuitComponents.values()) {
                        circuitComponentsShapes.add(elem.getShape());
                    }

                    for (Node shape : panel.getChildren()) {
                        if (selectionBounds.contains(shape.getBoundsInParent())
                                && circuitComponentsShapes.contains(shape)) {
                            if (firstLoop) {
                                minPos[0] = shape.getBoundsInParent().getMinX();
                                minPos[1] = shape.getBoundsInParent().getMinY();
                                maxPos[0] = shape.getBoundsInParent().getMaxX();
                                maxPos[1] = shape.getBoundsInParent().getMaxY();
                                firstLoop = false;
                            } else {
                                if (shape.getBoundsInParent().getMinX() < minPos[0])
                                    minPos[0] = shape.getBoundsInParent().getMinX();

                                if (shape.getBoundsInParent().getMinY() < minPos[1])
                                    minPos[1] = shape.getBoundsInParent().getMinY();
                                if (shape.getBoundsInParent().getMaxX() > maxPos[0])
                                    maxPos[0] = shape.getBoundsInParent().getMaxX();
                                if (shape.getBoundsInParent().getMaxY() > maxPos[1])
                                    maxPos[1] = shape.getBoundsInParent().getMaxY();
                            }
                            for (Map.Entry<Integer, GraphicElem> entry : circuitComponents.entrySet()) {
                                if (shape == entry.getValue().getShape()) {
                                    selectedElemsId.add(entry.getKey());
                                    break;
                                }
                            }
                        }
                    }
                    setSelectedElemRec(minPos, maxPos);
                }
            }

            selectionBounds = null;
        }
    }

    /**
     * Translates a graphical element and its associated components (e.g., input
     * circles).
     * 
     * - Updates the position of the element based on the provided translation
     * vector.
     * - Applies accumulated translation values for the element and the scene.
     * 
     * @param elem     The graphical element to be translated.
     * @param transVec A double array containing the x and y translation values.
     */
    private void translateElem(GraphicElem elem, double[] transVec) {
        double[] transVector = new double[2];
        transVector[0] = transVec[0] + transValue[0];
        transVector[1] = transVec[1] + transValue[1];

        if (selectedElemTransValue.get(elem.getId()) != null) {
            transVector[0] += selectedElemTransValue.get(elem.getId())[0];
            transVector[0] += selectedElemTransValue.get(elem.getId())[1];
        }

        elem.getShape().setTranslateX(transVector[0]);
        elem.getShape().setTranslateY(transVector[1]);

        for (Circle inputCircle : elem.getInputs()) {
            inputCircle.setTranslateX(transVector[0]);
            inputCircle.setTranslateY(transVector[1]);
        }

        // System.out.println("transVec[0]: " + transVector[0] + "transVec[1] : " +
        // transVector[1]);
        System.out.println("elemMinX " + elem.getShape().getBoundsInParent().getMinX() + "elemMinY"
                + elem.getShape().getBoundsInParent().getMinY());
    }

    private void zoom(double zoomFactor) {

    }

    /**
     * Retrieves the panel associated with the whiteboard.
     * 
     * - The panel serves as the main container for all graphical elements and user
     * interactions.
     * - It includes circuit components, selection rectangles, and other visual
     * elements.
     * 
     * @return The `Pane` object representing the whiteboard's panel.
     */
    public Pane getPanel() {
        return panel;
    }

    /**
     * Retrieves the number of columns in the grid.
     * 
     * - The grid is used to organize and position elements on the whiteboard.
     * 
     * @return The number of columns in the grid.
     */
    public int getGridColNb() {
        return gridColNb;
    }

    /**
     * Retrieves the grid ratio used in the whiteboard.
     * 
     * - The grid ratio defines the size of each grid cell in relation to the
     * whiteboard's dimensions.
     * - It is used to calculate the positioning and scaling of elements on the
     * grid.
     * 
     * @return The grid ratio as a `double`.
     */
    public double getGridRatio() {
        return gridRatio;
    }

    /**
     * Retrieves the number of rows in the grid.
     * 
     * - The grid is used to organize and position elements on the whiteboard.
     * 
     * @return The number of rows in the grid.
     */
    public int getGridLineNb() {
        return gridLineNb;
    }

    /**
     * Retrieves the map of circuit components on the whiteboard.
     * 
     * - The map associates unique IDs with their corresponding graphical elements.
     * - It is used to manage and access the elements displayed on the whiteboard.
     * 
     * @return A `HashMap` where the keys are unique IDs (`Integer`) and the values
     *         are `GraphicElem` objects.
     */
    public HashMap<Integer, GraphicElem> getHashMap() {
        return circuitComponents;
    }

}
