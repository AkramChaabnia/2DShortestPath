<p align="center">
  <a href="" rel="noopener">
    <img src="" alt="Project logo">
  </a>
</p>
<h3 align="center">2DShortestPath</h3>

<div align="center">

[![Status](https://img.shields.io/badge/status-active-success.svg)]()
[![GitHub Issues](https://img.shields.io/github/issues/AkramChaabnia/2DShortestPath.svg)](https://github.com/AkramChaabnia/2DShortestPath/issues)
[![GitHub Pull Requests](https://img.shields.io/github/issues-pr/AkramChaabnia/2DShortestPath.svg)](https://github.com/AkramChaabnia/2DShortestPath/pulls)
[![University](https://img.shields.io/badge/University-Paris%20Cité%20Université-%23A6192E)](https://u-paris.fr)

</div>

---

<p align="center"> 2DShortestPath is a Java project that compares the Dijkstra and A* algorithms for finding the shortest path between two points on a 2D map. It was developed as part of the advanced algorithms module at Université de Paris Cité (IF05X040 Algorithmique avancée).
  <br> 
</p>

## 📝 Table of Contents

- [About](#about)
- [Code Structure](#code_structure)
- [Evaluation](#evaluation)
- [Conclusion](#conclusion)
- [Map Format](#map_format)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting_started)
- [Usage](#usage)
- [Built Using](#built_using)
- [Authors](#authors)
- [Acknowledgments](#acknowledgement)
- [Contact](#contact)

## 🧐 About <a name = "about"></a>

The goal of this project is to implement and compare the Dijkstra and A\* algorithms for finding the shortest path on a 2D map. The project includes a weighted graph structure, methods for running the algorithms and displaying the map, and different maps encoded in text files. The project also evaluates the performance of the two algorithms and reflects on their real use cases.

## 🚀 Code Structure <a name = "code_structure"></a>

The project consists of the following files:

- `WeightGraph.java`: Defines a weighted graph structure that represents the 2D map.
- `App.java`: Includes several methods:
  - A `main` method: Reads the map encoded in a text file, represents it as a graph, and launches the shortest path search with one of the two algorithms.
  - A `Dijkstra` method: Finds the shortest path between the start and end point using the Dijkstra algorithm.
  - An `AStar` method: Finds the shortest path between the start and end point using the A\* algorithm.
  - A `drawBoard` method and a `Board` class: Manage the display of the map, the running of the algorithms, and the path found.
- `map.txt`: A text file that encodes the map to be used for the shortest path search.
- `out.txt`: Contains the result which is the path from start to end node.

## 🎈 Evaluation <a name="evaluation"></a>

After completing the code, I have evaluated and compared the performances of Dijkstra and A\* (proposing one or more heuristics) on different (relevant) maps.

### Dijkstra Evaluation

- Number of nodes explored: 5000
- Total nodes of the path: 207
- Total time of the path: 273.0

![Dijkstra Screenshot](https://i.imgur.com/WQVGfhD.png)

### A\* Evaluation

- Number of nodes explored: 4079
- Total nodes of the path: 215
- Total time of the path: 286.0

![A* Screenshot](https://i.imgur.com/Adis2Ns.png)

Evaluation:

- Dijkstra algorithm: The Dijkstra algorithm guarantees finding the shortest path between two vertices, but it explores all possible paths, which can be time-consuming for large graphs.
- A* algorithm: The A* algorithm is an improvement over Dijkstra as it uses heuristics to guide the search and prioritize paths that are more likely to lead to the goal. This can significantly reduce the search space and improve performance.

Comparison:

- Performance: In general, the A\* algorithm performs better than Dijkstra, especially on larger graphs, due to its ability to prioritize paths.
- Heuristics: The choice of heuristics can greatly impact the performance of the A\* algorithm. Different heuristics can lead to different results in terms of path length and computation time.

Reflection on Real Use Cases:

- Dijkstra algorithm: Dijkstra is suitable for scenarios where finding the absolute shortest path is crucial, regardless of the computational cost.
- A* algorithm: A* is more suitable for scenarios where finding an efficient path is important, and the computational cost can be reduced by using appropriate heuristics.

## 📄 Conclusion <a name="conclusion"></a>

In conclusion, the 2DShortestPath project successfully compares the Dijkstra and A* algorithms for finding the shortest path on a 2D map. The A* algorithm outperforms Dijkstra in terms of performance, especially on larger graphs, thanks to its ability to prioritize paths using heuristics. The choice of heuristics can greatly impact the performance of the A* algorithm, leading to different results in terms of path length and computation time. Overall, both algorithms have their use cases, with Dijkstra being suitable for scenarios where finding the absolute shortest path is crucial, and A* being more suitable for scenarios where finding an efficient path is important while considering computational cost.

## 📄 Map Format <a name="map_format"></a>

The map is encoded in a text file with the following format (the parts to be replaced are indicated by <>):

==Metadata==

=Size=

nlines=<int: The number of lines>

ncol=<int: The number of columns>

=Types=

G=<int: The time required to travel vertically or horizontally through a square of this type>

<string: The color of the square of the previous type (usable without code modification: "green", "gray", "blue" and "yellow")>

W=<int: The time required to travel through a square of this type>

<string: The color of the square of the previous type (usable without code modification: "green", "gray", "blue" and "yellow")>

...

==Graph==

<string: A succession of ncols letters, according to the types defined previously.>

<string: A succession of ncols letters, according to the types defined previously.>

... (nlines times)

==Path==

Start=<int,int: the coordinates (line, column) of the starting point>

Finish=<int,int: the coordinates (line, column) of the arrival point>

You can define your own maps by modifying the one provided.

## 🛠️ Prerequisites <a name = "prerequisites"></a>

To run the project, ensure the following are installed:

- [Java](https://www.java.com/en/download/): Download and install Java on your machine.
- [JavaFX](https://openjfx.io/): Install JavaFX and configure it in your IDE if you're using one.

## 🏁 Getting Started <a name = "getting_started"></a>

To compile and execute the project:

1. Clone the repository or download the project files.

### Using an IDE (Eclipse, VSCode, etc.)

2. Set up the JavaFX environment in your IDE.
3. Run the `App.java` file.

### Using Command Line

2. Compile the Java code using the command: `javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls App.java`.
3. Run the compiled code using the command: `java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls App`.

## 🎈 Usage <a name="usage"></a>

To use the project, you can follow these steps:

- Choose or create a map file that follows the format described above.
- In the `main` method of the `App` class, specify the name of the map file and the algorithm to use (Dijkstra or AStar).
- Run the `App` class and observe the output. The output will show the map, the path found, and the performance metrics of the algorithm.
- You can change the map file or the algorithm and run the project again to compare the results.

## ⛏️ Built Using <a name = "built_using"></a>

- [Java](https://www.java.com/) - Programming Language

## ✍️ Authors <a name = "authors"></a>

- Sylvain LOBRY - Universite de Paris Cite (IF05X040 Algorithmique avancee) - Idea & Initial work
- [@AkramChaabnia](https://github.com/AkramChaabnia) - Completion & Finalization

## 🎉 Acknowledgements <a name = "acknowledgement"></a>

- This project was inspired by the advanced algorithms module at Université de Paris Cité (IF05X040 Algorithmique avancée).
- The code skeleton and the map format were provided by the module instructor, <u><b>Professor Nicolas Loménie</b></u>.

## 📞 Contact <a name = "contact"></a>

If you have any questions, suggestions, or just want to connect, here's how you can reach me:

- Email: akram.chaabnia25@gmail.com
- LinkedIn: [Akram CHAABNIA](https://www.linkedin.com/in/akram-chaabnia-43b7941b0/)
- GitHub: [@AkramChaabnia](https://github.com/AkramChaabnia)

Please feel free to contact me!
