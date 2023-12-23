<p align="center">
  <a href="" rel="noopener">
 <img src="https://i.imgur.com/AZ2iWek.png" alt="Project logo"></a>
</p>
<h3 align="center">2DShortestPath</h3>

<div align="center">

[![Status](https://img.shields.io/badge/status-active-success.svg)]()
[![GitHub Issues](https://img.shields.io/github/issues/kylelobo/The-Documentation-Compendium.svg)](https://github.com/kylelobo/The-Documentation-Compendium/issues)
[![GitHub Pull Requests](https://img.shields.io/github/issues-pr/kylelobo/The-Documentation-Compendium.svg)](https://github.com/kylelobo/The-Documentation-Compendium/pulls)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE.md)

</div>
2DShortestPath is a graph theory project for the advanced algorithms module at Université de Paris Cité (IF05X040 Algorithmique avancée). It compares the Dijkstra and A* algorithms for finding the shortest path between two vertices on a 2D map.
## Objective

## The goal of this exercise is to find the shortest path on a 2D map. The provided code includes the structure for implementing the Dijkstra and A\* algorithms, as well as the methods for running the algorithms and displaying the map. By completing the code, i was be able to implement and compare the performances of the two algorithms on different maps.

<p align="center"> 2DShortestPath is a graph theory project that compares the Dijkstra and A* algorithms for finding the shortest path between two vertices, or in this context, on a 2D map. The project includes a WeightedGraph class to define the structure of the weighted graph. It also includes an App class that contains the methods to define the Dijkstra and A* algorithms, drawBoard and Board for displaying the map, running the algorithms, and finding the path. The main method reads the map encoded in a text file, represents it as a graph, and performs the shortest path search using one of the two algorithms.
    <br> 
</p>

## Code Structure

A code skeleton has been provided, which includes:

- `WeightGraph.java`: Defines a weighted graph structure.
- `App.java`: Includes several methods:
  - A `main` method: You have read the map encoded in a text file, represented it as a graph (using the given structure), and launched the shortest path search with one of the two completed algorithms.
  - A `Dijkstra` and an `AStar` method: These have found the path between the start and end vertex from the graph.
  - A `drawBoard` method and a `Board` class: These manage the display of the map, the running of the algorithms, and the path found.

## Evaluation

After completing the code, I have evaluated and compared the performances of Dijkstra and A\* (proposing one or more heuristics) on different (relevant) maps.

Evaluation:

- Dijkstra algorithm: The Dijkstra algorithm guarantees finding the shortest path between two vertices, but it explores all possible paths, which can be time-consuming for large graphs.
- A* algorithm: The A* algorithm is an improvement over Dijkstra as it uses heuristics to guide the search and prioritize paths that are more likely to lead to the goal. This can significantly reduce the search space and improve performance.

Comparison:

- Performance: In general, the A\* algorithm performs better than Dijkstra, especially on larger graphs, due to its ability to prioritize paths.
- Heuristics: The choice of heuristics can greatly impact the performance of the A\* algorithm. Different heuristics can lead to different results in terms of path length and computation time.

Reflection on Real Use Cases:

- Dijkstra algorithm: Dijkstra is suitable for scenarios where finding the absolute shortest path is crucial, regardless of the computational cost.
- A* algorithm: A* is more suitable for scenarios where finding an efficient path is important, and the computational cost can be reduced by using appropriate heuristics.

## Map Format

The graph was provided in the form of a text file. The format to respect is as follows:

- `==Metadata==`
- `=Size=`
  - `nlines=<int : The number of lines>`
  - `ncol=<int: The number of columns>`
- `=Types=`
  - `G=<int: The time needed to traverse vertically or horizontally a box of this type>`
  - `<string : The color of the box of the previous type (can be used without code modification: "green", "gray", "blue" and "yellow")>`
  - `W=<int: The time needed to traverse a box of this type>`
  - `<string : The color of the box of the previous type (can be used without code modification: "green", "gray", "blue" and "yellow")>`

## Conclusion

In conclusion, the 2DShortestPath project successfully compares the Dijkstra and A* algorithms for finding the shortest path on a 2D map. The A* algorithm outperforms Dijkstra in terms of performance, especially on larger graphs, thanks to its ability to prioritize paths using heuristics. The choice of heuristics can greatly impact the performance of the A* algorithm, leading to different results in terms of path length and computation time. Overall, both algorithms have their use cases, with Dijkstra being suitable for scenarios where finding the absolute shortest path is crucial, and A* being more suitable for scenarios where finding an efficient path is important while considering computational cost.

## Installation

To install the software, you will need the following:

- Java Development Kit (JDK)
- Eclipse IDE or Visual Studio Code

Follow these steps to install the software:

1. Download and install the latest version of JDK from the official Oracle website.
2. Download and install Eclipse IDE or Visual Studio Code from their respective official websites.
3. Clone the repository or download the project files.
4. Open the project in your chosen IDE.
5. Build and run the `App.java` file to start the program.

## Usage

To use the project, follow these steps:

1. Clone the repository.
2. Open the project in your preferred IDE.
3. Run the `App.java` file.
4. Follow the on-screen instructions to input the path to the text file containing the map.
5. The program will display the shortest path between the start and end vertices using either the Dijkstra or A\* algorithm.

## Technology Stack <a name = "tech_stack"></a>

- Java
- Eclipse IDE
- Visual Studio Code

## Authors <a name = "authors"></a>

- Sylvain LOBRY - Univesite de Paris Cite (IF05X040 Algorithmique avancee)
- Akram CHAABNIA - [@AkramChaabnia](https://github.com/AkramChaabnia)
