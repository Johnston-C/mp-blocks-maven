# mp-blocks-maven

Explorations with the creation, composition, and mutation of blocks of ASCII text.

**Authors**

* Samuel A. Rebelsky (starter code).
* Cade Johnston
* Nicky Moreno Gonzalez

**Notes for the reader**

In this mini-project, we explored subtype polymorphism by extending the AsciiBlock class to handle more complex text layouts. We implemented classes such as Grid, HComp, VComp, HFlip, VFlip, Trimmed, and Padded to manipulate ASCII blocks, ensuring they adapt to changes in the underlying blocks. Additionally, we have developed a custom block type 'BezierCurveStamp' and implemented the eqv method for each Asciiblock, which compares blocks based on how they were constructed, rather than just their appearance. The main method of this project displays an artwork in which we use multiple types of AsciiBlocks.

**Citations**

The BezierCurveStamp was inspired by "The Continuity of Splines" and "The Beauty of Bézier Curves" by Freya Holmér. They can be found at <https://www.youtube.com/watch?v=jvPPXbo87ds> and <https://www.youtube.com/watch?v=aVwxzDHniEw> respectively. The formulas visible for the cubic bezier curve shown from 4:37 to 7:52 in "The Beauty of Bézier Curves" and from 5:09 to 7:44 in "The Continuity of Splines" inspired the now generalized function found in createData() within BezierCurveStamp. Further, the concept of splines discussed in "The Continuity of Splines" led to BezierCurveStamp working more like a series of splines in the end. This implementation, however, is our own and was developed with little reference to the functions shown.

---

This code may be found at <https://github.com/Johnston-C/mp-blocks-maven.git>. The original project can be found at <https://github.com/Grinnell-CSC207/mp-blocks-maven>.
