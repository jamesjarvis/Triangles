/*
Author: James Jarvis

This version has a GUI representation. To run each problem, click on the graph
that is shown on screen.
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.LinkedList;

public class Problems{

    private LinkedList<Triangle> triangles;//Contains the triangle 'obstacles'

    private LinkedList<Route> routes;//Contains all Routes, which themselves contain Vertex objects for each point.

    private LinkedList<Vertex[]> problems;//Contains all the problems to solve

    public Problems() {
        this.triangles = new LinkedList<>();
        this.routes = new LinkedList<>();
        this.problems = new LinkedList<>();
    }

    public boolean readFile(String fileName){
        try {
            File fXmlFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            doc.getDocumentElement().normalize();

            Node triangles = doc.getElementsByTagName("triangles").item(0);
            readTriangles(triangles.getChildNodes());

            Node problems = doc.getElementsByTagName("problems").item(0);
            readProblems(problems.getChildNodes());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Reads the triangles in to the triangles list
     * @param trianglesList
     */
    public void readTriangles(NodeList trianglesList){
        for (int index = 0; index < trianglesList.getLength(); index++) {

            Node triangle = trianglesList.item(index);

            if(triangle.getNodeType() == Node.ELEMENT_NODE) {
                Element triangleElement = (Element) triangle;

                NodeList vertices = triangleElement.getChildNodes();

                Triangle newTriangle = new Triangle();//New triangle to be added

                for(int vertexNo = 0; vertexNo < vertices.getLength(); vertexNo++){
                    Node vertex = vertices.item(vertexNo);

                    if(vertex.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) vertex;

                        Vertex v = new Vertex(
                                Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent()),
                                Integer.parseInt(element.getElementsByTagName("y").item(0).getTextContent())
                        );

                        newTriangle.addVertex(v);
                    }
                }

                newTriangle.drawTriangle();

                triangles.add(newTriangle);

            }
        }
        System.out.println("Read "+triangles.size()+" triangles successfully");
    }

    /**
     * Just returns the linkedlist of triangles
     * @return LinkedList of triangle objects
     */
    public LinkedList<Triangle> getTriangles() {
        return triangles;
    }

    /**
     * Reads the problems in to the triangles list
     * @param problemsList
     */
    public void readProblems(NodeList problemsList){
        for (int index = 0; index < problemsList.getLength(); index++) {

            Node problemNode = problemsList.item(index);

            if(problemNode.getNodeType() == Node.ELEMENT_NODE) {
                Element problemElement = (Element) problemNode;

                NodeList vertices = problemElement.getChildNodes();

                Vertex[] problemVertices = new Vertex[2];//New problem vertices (start and end)

                for(int vertexNo = 0; vertexNo < vertices.getLength(); vertexNo++){
                    Node vertex = vertices.item(vertexNo);

                    if(vertex.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) vertex;

                        if(element.getAttribute("id").equals("start")){
                            problemVertices[0] = new Vertex(
                                    Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent()),
                                    Integer.parseInt(element.getElementsByTagName("y").item(0).getTextContent())
                            );
                        }else if(element.getAttribute("id").equals("end")){
                            problemVertices[1] = new Vertex(
                                    Integer.parseInt(element.getElementsByTagName("x").item(0).getTextContent()),
                                    Integer.parseInt(element.getElementsByTagName("y").item(0).getTextContent())
                            );
                        }
                    }
                }

                problems.add(problemVertices);

            }
        }
        System.out.println("Read "+problems.size()+" problems successfully");
    }

    /**
     * @return Linkedlist of all problems
     */
    public LinkedList<Vertex[]> getProblems() {
        return problems;
    }
}
