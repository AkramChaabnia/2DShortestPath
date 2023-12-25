// Par Sylvain Lobry, pour le cours "IF05X040 Algorithmique avanc�e"
// de l'Universit� de Paris, 11/2020

package up.mi.chaabnia;

import up.mi.chaabnia.WeightedGraph.Edge;
import up.mi.chaabnia.WeightedGraph.Graph;
import up.mi.chaabnia.WeightedGraph.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JFrame;

//Classe pour g�rer l'affichage
class Board extends JComponent {
	private static final long serialVersionUID = 1L;
	Graph graph;
	int pixelSize;
	int ncols;
	int nlines;
	HashMap<Integer, String> colors;
	int start;
	int end;
	double max_distance;
	int current;
	LinkedList<Integer> path;

	public Board(Graph graph, int pixelSize, int ncols, int nlines, HashMap<Integer, String> colors, int start, int end) {
		super();
		this.graph = graph;
		this.pixelSize = pixelSize;
		this.ncols = ncols;
		this.nlines = nlines;
		this.colors = colors;
		this.start = start;
		this.end = end;
		this.max_distance = ncols * nlines;
		this.current = -1;
		this.path = null;
	}

	// Mise � jour de l'affichage
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// Ugly clear of the frame
		g2.setColor(Color.cyan);
		g2.fill(new Rectangle2D.Double(0, 0, this.ncols * this.pixelSize, this.nlines * this.pixelSize));

		int num_case = 0;
		for (WeightedGraph.Vertex v : this.graph.vertexlist) {
			double type = v.indivTime;
			int i = num_case / this.ncols;
			int j = num_case % this.ncols;

			if (colors.get((int) type).equals("green"))
				g2.setPaint(Color.green);
			if (colors.get((int) type).equals("gray"))
				g2.setPaint(Color.gray);
			if (colors.get((int) type).equals("blue"))
				g2.setPaint(Color.blue);
			if (colors.get((int) type).equals("yellow"))
				g2.setPaint(Color.yellow);
			g2.fill(new Rectangle2D.Double(j * this.pixelSize, i * this.pixelSize, this.pixelSize, this.pixelSize));

			if (num_case == this.current) {
				g2.setPaint(Color.red);
				g2.draw(new Ellipse2D.Double(j * this.pixelSize + this.pixelSize / 2, i * this.pixelSize + this.pixelSize / 2,
						6, 6));
			}
			if (num_case == this.start) {
				g2.setPaint(Color.white);
				g2.fill(new Ellipse2D.Double(j * this.pixelSize + this.pixelSize / 2, i * this.pixelSize + this.pixelSize / 2,
						4, 4));

			}
			if (num_case == this.end) {
				g2.setPaint(Color.black);
				g2.fill(new Ellipse2D.Double(j * this.pixelSize + this.pixelSize / 2, i * this.pixelSize + this.pixelSize / 2,
						4, 4));
			}

			num_case += 1;
		}

		num_case = 0;
		for (WeightedGraph.Vertex v : this.graph.vertexlist) {
			int i = num_case / this.ncols;
			int j = num_case % this.ncols;
			if (v.timeFromSource < Double.POSITIVE_INFINITY) {
				float g_value = (float) (1 - v.timeFromSource / this.max_distance);
				if (g_value < 0)
					g_value = 0;
				g2.setPaint(new Color(g_value, g_value, g_value));
				g2.fill(new Ellipse2D.Double(j * this.pixelSize + this.pixelSize / 2, i * this.pixelSize + this.pixelSize / 2,
						4, 4));
				WeightedGraph.Vertex previous = v.prev;
				if (previous != null) {
					int i2 = previous.num / this.ncols;
					int j2 = previous.num % this.ncols;
					g2.setPaint(Color.black);
					g2.draw(new Line2D.Double(j * this.pixelSize + this.pixelSize / 2, i * this.pixelSize + this.pixelSize / 2,
							j2 * this.pixelSize + this.pixelSize / 2, i2 * this.pixelSize + this.pixelSize / 2));
				}
			}

			num_case += 1;
		}

		int prev = -1;
		if (this.path != null) {
			g2.setStroke(new BasicStroke(3.0f));
			for (int cur : this.path) {
				if (prev != -1) {
					g2.setPaint(Color.red);
					int i = prev / this.ncols;
					int j = prev % this.ncols;
					int i2 = cur / this.ncols;
					int j2 = cur % this.ncols;
					g2.draw(new Line2D.Double(j * this.pixelSize + this.pixelSize / 2, i * this.pixelSize + this.pixelSize / 2,
							j2 * this.pixelSize + this.pixelSize / 2, i2 * this.pixelSize + this.pixelSize / 2));
				}
				prev = cur;
			}
		}
	}

	// Mise � jour du graphe (� appeler avant de mettre � jour l'affichage)
	public void update(Graph graph, int current) {
		this.graph = graph;
		this.current = current;
		repaint();
	}

	// Indiquer le chemin (pour affichage)
	public void addPath(Graph graph, LinkedList<Integer> path) {
		this.graph = graph;
		this.path = path;
		this.current = -1;
		repaint();
	}
}

// Classe principale. C'est ici que vous devez faire les modifications
public class App {

	// Initialise l'affichage
	private static void drawBoard(Board board, int nlines, int ncols, int pixelSize) {
		JFrame window = new JFrame("Plus court chemin");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, ncols * pixelSize + 20, nlines * pixelSize + 40);
		window.getContentPane().add(board);
		window.setVisible(true);
	}

	// M�thode A*
	// graph: le graphe repr�sentant la carte
	// start: un entier repr�sentant la case de d�part
	// (entier unique correspondant � la case obtenue dans le sens de la lecture)
	// end: un entier repr�sentant la case d'arriv�e
	// (entier unique correspondant � la case obtenue dans le sens de la lecture)
	// ncols: le nombre de colonnes dans la carte
	// numberV: le nombre de cases dans la carte
	// board: l'affichage
	// retourne une liste d'entiers correspondant au chemin.
	private static LinkedList<Integer> AStar(Graph graph, int start, int end, int ncols, int numberV, Board board) {
		graph.vertexlist.get(start).timeFromSource = 0;
		int number_tries = 0;

		// Put all nodes of the graph in the list of nodes to visit:
		HashSet<Integer> to_visit = new HashSet<Integer>();
		for (int i = 0; i < graph.vertexlist.size(); i++) {
			to_visit.add(i);
		}

		// Fill the attribute graph.vertexlist.get(v).heuristic for all nodes v of the
		// graph:
		for (Vertex v : graph.vertexlist) {
			v.heuristic = Math.abs(v.num / ncols - end / ncols) + Math.abs(v.num % ncols - end % ncols);
			System.out.println("Setting heuristic for vertex " + v.num + ": " + v.heuristic);
		}

		while (to_visit.contains(end)) {
			// Find the node min_v among all nodes v having the temporary distance
			// (graph.vertexlist.get(v).timeFromSource + heuristic) minimal.
			int min_v = -1;
			double min_distance = Double.MAX_VALUE;
			for (int v : to_visit) {
				double distance = graph.vertexlist.get(v).timeFromSource +
						graph.vertexlist.get(v).heuristic;
				if (distance < min_distance) {
					min_distance = distance;
					min_v = v;
				}
			}

			System.out.println("Min vertex: " + min_v + ", Min distance: " + min_distance);

			// Remove it from the nodes to visit
			to_visit.remove(min_v);
			number_tries += 1;

			// For all its neighbors, we check if we are faster by going through this node.
			for (int i = 0; i < graph.vertexlist.get(min_v).adjacencylist.size(); i++) {
				int to_try = graph.vertexlist.get(min_v).adjacencylist.get(i).destination;
				double newTimeFromSource = graph.vertexlist.get(min_v).timeFromSource
						+ graph.vertexlist.get(min_v).adjacencylist.get(i).weight;
				if (newTimeFromSource < graph.vertexlist.get(to_try).timeFromSource) {
					System.out.println("Updating timeFromSource for vertex " + to_try);
					graph.vertexlist.get(to_try).timeFromSource = newTimeFromSource;
					graph.vertexlist.get(to_try).prev = new Vertex();
					graph.vertexlist.get(to_try).prev.num = min_v;
				}
			}

			// Update the display
			try {
				board.update(graph, min_v);
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("stop");
			}
		}

		System.out.println("Done! Using A*:");
		System.out.println(" Number of nodes explored: " + number_tries);
		System.out.println(" Total time of the path: " +
				graph.vertexlist.get(end).timeFromSource);
		LinkedList<Integer> path = new LinkedList<Integer>();
		int current = end;
		while (current != start) {
			path.addFirst(current);
			if (graph.vertexlist.get(current).prev != null) {
				current = graph.vertexlist.get(current).prev.num;
			} else {
				// Handle the case where prev is null
				System.out.println("No path found from start to current node");
				break;
			}
		}
		path.addFirst(start);

		board.addPath(graph, path);
		return path;
	}

	// graph: le graphe repr�sentant la carte
	// start: un entier repr�sentant la case de d�part
	// (entier unique correspondant � la case obtenue dans le sens de la lecture)
	// end: un entier repr�sentant la case d'arriv�e
	// (entier unique correspondant � la case obtenue dans le sens de la lecture)
	// numberV: le nombre de cases dans la carte
	// board: l'affichage
	// retourne une liste d'entiers correspondant au chemin.

	private static LinkedList<Integer> Dijkstra(Graph graph, int start, int end, int numberV, Board board) {
		System.out.println("Start node: " + start); // Print the start node
		System.out.println("End node: " + end); // Print the end node

		PriorityQueue<Vertex> to_visit = new PriorityQueue<>(numberV, Comparator.comparingDouble(v -> v.timeFromSource));

		for (int i = 0; i < numberV; i++) {
			graph.vertexlist.get(i).timeFromSource = Double.MAX_VALUE;
			to_visit.add(graph.vertexlist.get(i));
		}

		graph.vertexlist.get(start).timeFromSource = 0;

		int number_tries = 0;

		while (!to_visit.isEmpty()) {
			Vertex min_v = to_visit.poll();
			if (min_v == null) {
				System.out.println("No minimum vertex found!");
				break;
			}

			number_tries += 1;

			System.out.println("Exploring node: " + min_v.num);

			for (Edge edge : min_v.adjacencylist) {
				int to_try = edge.destination;
				double new_dist = min_v.timeFromSource + edge.weight;

				System.out.println("Processing edge from " + min_v.num + " to " + to_try + " with weight " + edge.weight);

				if (new_dist < graph.vertexlist.get(to_try).timeFromSource) {
					graph.vertexlist.get(to_try).timeFromSource = new_dist;
					graph.vertexlist.get(to_try).prev = min_v;

					// Remove and re-add the updated node to reflect the new distance
					to_visit.remove(graph.vertexlist.get(to_try));
					to_visit.add(graph.vertexlist.get(to_try));

					System.out.println("Updated distance for node " + to_try + ": " + new_dist);
				}
			}

			try {
				board.update(graph, min_v.num);
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("stop");
			}
		}

		System.out.println("Done! Using Dijkstra:");
		System.out.println(" Number of nodes explored: " + number_tries);
		System.out.println(" Total time of the path: " + graph.vertexlist.get(end).timeFromSource);

		LinkedList<Integer> path = new LinkedList<>();
		path.addFirst(end);
		int current = end;
		while (current != start) {
			Vertex prevVertex = graph.vertexlist.get(current).prev;
			if (prevVertex != null) {
				current = prevVertex.num;
				path.addFirst(current);
			} else {
				System.out.println("No path found from start to end node.");
				break;
			}
		}

		board.addPath(graph, path);
		return path;
	}

	// M�thode principale
	public static void main(String[] args) {
		// Lecture de la carte et cr�ation du graphe
		try {
			// TODO: obtenir le fichier qui d�crit la carte
			File myObj = new File("graph.txt");
			Scanner myReader = new Scanner(myObj);
			String data = "";
			// On ignore les deux premi�res lignes
			for (int i = 0; i < 3; i++)
				data = myReader.nextLine();

			// Lecture du nombre de lignes
			int nlines = Integer.parseInt(data.split("=")[1]);
			// Et du nombre de colonnes
			data = myReader.nextLine();
			int ncols = Integer.parseInt(data.split("=")[1]);

			// Initialisation du graphe
			Graph graph = new Graph();

			HashMap<String, Integer> groundTypes = new HashMap<String, Integer>();
			HashMap<Integer, String> groundColor = new HashMap<Integer, String>();
			data = myReader.nextLine();
			data = myReader.nextLine();
			// Lire les diff�rents types de cases
			while (!data.equals("==Graph==")) {
				String name = data.split("=")[0];
				int time = Integer.parseInt(data.split("=")[1]);
				data = myReader.nextLine();
				String color = data;
				groundTypes.put(name, time);
				groundColor.put(time, color);
				data = myReader.nextLine();
			}

			// On ajoute les sommets dans le graphe (avec le bon type)
			for (int line = 0; line < nlines; line++) {
				data = myReader.nextLine();
				for (int col = 0; col < ncols; col++) {
					graph.addVertex(groundTypes.get(String.valueOf(data.charAt(col))));
				}
			}

			// TODO: ajouter les arretes
			for (int line = 0; line < nlines; line++) {
				for (int col = 0; col < ncols; col++) {
					int source = line * ncols + col;
					int dest;
					double weight;

					// Add edge to the node above
					if (line > 0) {
						dest = (line - 1) * ncols + col;
						weight = (graph.vertexlist.get(source).indivTime + graph.vertexlist.get(dest).indivTime) / 2.0;
						graph.addEdge(source, dest, weight);
					}

					// Add edge to the node below
					if (line < nlines - 1) {
						dest = (line + 1) * ncols + col;
						weight = (graph.vertexlist.get(source).indivTime + graph.vertexlist.get(dest).indivTime) / 2.0;
						graph.addEdge(source, dest, weight);
					}

					// Add edge to the node on the left
					if (col > 0) {
						dest = line * ncols + (col - 1);
						weight = (graph.vertexlist.get(source).indivTime + graph.vertexlist.get(dest).indivTime) / 2.0;
						graph.addEdge(source, dest, weight);
					}

					// Add edge to the node on the right
					if (col < ncols - 1) {
						dest = line * ncols + (col + 1);
						weight = (graph.vertexlist.get(source).indivTime + graph.vertexlist.get(dest).indivTime) / 2.0;
						graph.addEdge(source, dest, weight);
					}
					// Add edge to the node on the top-left diagonal
					if (line > 0 && col > 0) {
						dest = (line - 1) * ncols + (col - 1);
						weight = (graph.vertexlist.get(source).indivTime + graph.vertexlist.get(dest).indivTime) / 2.0;
						graph.addEdge(source, dest, weight);
					}

					// Add edge to the node on the top-right diagonal
					if (line > 0 && col < ncols - 1) {
						dest = (line - 1) * ncols + (col + 1);
						weight = (graph.vertexlist.get(source).indivTime + graph.vertexlist.get(dest).indivTime) / 2.0;
						graph.addEdge(source, dest, weight);
					}

					// Add edge to the node on the bottom-left diagonal
					if (line < nlines - 1 && col > 0) {
						dest = (line + 1) * ncols + (col - 1);
						weight = (graph.vertexlist.get(source).indivTime + graph.vertexlist.get(dest).indivTime) / 2.0;
						graph.addEdge(source, dest, weight);
					}

					// Add edge to the node on the bottom-right diagonal
					if (line < nlines - 1 && col < ncols - 1) {
						dest = (line + 1) * ncols + (col + 1);
						weight = (graph.vertexlist.get(source).indivTime + graph.vertexlist.get(dest).indivTime) / 2.0;
						graph.addEdge(source, dest, weight);
					}
				}
			}

			// On obtient les noeuds de d�part et d'arriv�
			data = myReader.nextLine();
			data = myReader.nextLine();
			int startRow = Integer.parseInt(data.split("=")[1].split(",")[0]);
			int startCol = Integer.parseInt(data.split("=")[1].split(",")[1]);
			int startV = startRow * ncols + startCol;

			data = myReader.nextLine();
			int endRow = Integer.parseInt(data.split("=")[1].split(",")[0]);
			int endCol = Integer.parseInt(data.split("=")[1].split(",")[1]);
			int endV = endRow * ncols + endCol;

			// A changer pour avoir un affichage plus ou moins grand
			int pixelSize = 10;
			Board board = new Board(graph, pixelSize, ncols, nlines, groundColor, startV, endV);
			drawBoard(board, nlines, ncols, pixelSize);
			board.repaint();

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("stop");
			}

			if (startRow < 0 || startRow >= nlines || startCol < 0 || startCol >= ncols ||
					endRow < 0 || endRow >= nlines || endCol < 0 || endCol >= ncols) {
				System.out.println("Invalid start or end node coordinates!");
				// Handle the error condition here
			} else {

				// TODO: laisser le choix entre Dijkstra et A*
				System.out.println("Choose the algorithm: ");
				System.out.println("1. Dijkstra");
				System.out.println("2. A*");

				int choice = 0;
				Scanner scanner = new Scanner(System.in);
				choice = scanner.nextInt();

				LinkedList<Integer> path = null;

				if (choice == 1) {
					// Call Dijkstra algorithm
					path = Dijkstra(graph, startV, endV, nlines, board);
				} else if (choice == 2) {
					// Call A* algorithm
					path = AStar(graph, startV, endV, nlines, ncols, board);
				} else {
					System.out.println("Invalid choice!");
				}
				myReader.close();
				scanner.close();

				// �criture du chemin dans un fichier de sortie
				try {
					File file = new File("out.txt");
					if (!file.exists()) {
						file.createNewFile();
					}
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);

					for (int i : path) {
						bw.write(String.valueOf(i));
						bw.write('\n');
					}
					bw.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found or an error occurred while processing the file.");
			e.printStackTrace();
		}
	}

}
