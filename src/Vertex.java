/*
Author: Andy King
Used by: James Jarvis
Kent Login: jj333
 */
/*
This class is for Vertex comparisons.
 */

import java.util.*;

public class Vertex implements Comparable<Vertex>
{
    private int x;
    private int y;

    public Vertex(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int get_x()
    {
        return x;
    }

    public int get_y()
    {
        return y;
    }

    public int compareTo(Vertex v)
    {
        if (v.get_x() == get_x())
        {
            return v.get_y() - get_y();
        }
        else
        {
            return v.get_x() - get_x();
        }
    }

    public static boolean vertexIntersect(Vertex u, Vertex v1, Vertex v2)
    {
	/* check whether the vertex u falls within a given line segment where
           the line segment connects vertices v1 and v2 (but does not include them) */

        int a = v1.get_x() - v2.get_x();
        int b = u.get_x() - v2.get_x();

        int c = v1.get_y() - v2.get_y();
        int d = u.get_y() - v2.get_y();

	/* check whether the simultaneous equations a mu = b and c mu = d
           has a solution 0 < mu < 1
           where a and c are coefficients of the variable mu
           where b and d are constants */

        if (a < 0)
        {
            a = -a;
            b = -b;
        }

        if (c < 0)
        {
            c = -c;
            d = -d;
        }

        if (a == 0 && b == 0)
        {
            return 0 < d && d < c;
        }
        else if (c == 0 && d == 0)
        {
            return 0 < b && b < a;
        }
        else
        {
            return 0 < b && b < a && 0 < d && d < c && a * d == b * c;
        }
    }

    public static boolean linesIntersect(Vertex u1, Vertex u2, Vertex v1, Vertex v2)
    {
	/* check whether line segment 1 intersects line segment 2 where
           line segment 1 connects vertices u1 and u2 (but does not include them) and
           line segment 2 connects vertices v1 and v2 (but does not include them) */

        int a = u1.get_x() - u2.get_x();
        int b = v2.get_x() - v1.get_x();
        int c = v2.get_x() - u2.get_x();

        int d = u1.get_y() - u2.get_y();
        int e = v2.get_y() - v1.get_y();
        int f = v2.get_y() - u2.get_y();

	/* check whether the simultaneous equations a mu + b lambda = c and d mu + e lambda = f
           has a solution 0 < mu < 1 and 0 < lambda < 1
           where a and d are coefficients of the variable mu
           where b and e are coefficients of the variable lambda
           where c and f are constants */

        Predicate intersectPredicate = (int denominator, int lambda_numerator, int mu_numerator) ->
                0 < lambda_numerator && lambda_numerator < denominator &&
                        0 < mu_numerator && mu_numerator < denominator;

        return solve(a, b, c, d, e, f, intersectPredicate);
    }

    public static boolean vertexInterior(Vertex u, Triangle t){
        Vertex[] vertices = t.getPoints();
        return vertexInterior(u,vertices[0], vertices[1], vertices[2]);
    }

    public static boolean vertexInterior(Vertex u, Vertex v1, Vertex v2, Vertex v3)
    {
	/* check whether u occurs inside the solid triangle defined the by three
	   vertices v1, v2 and v3.  The check returns false if u occurs in the
           perimeter of the triangle; it only returns true if u occurs in the interior
	   of the triangle. */

        int a = v1.get_x() - v3.get_x();
        int b = v2.get_x() - v3.get_x();
        int c = u.get_x() - v3.get_x();

        int d = v1.get_y() - v3.get_y();
        int e = v2.get_y() - v3.get_y();
        int f = u.get_y() - v3.get_y();

	/* check whether the simultaneous equations a mu + b lambda = c and d mu + e lambda = f
           has a solution 0 < mu, 0 < lambda and mu + lambda < 1
           where a and d are coefficients of the variable mu
           where b and e are coefficients of the variable lambda
           where c and f are constants */

        Predicate interiorPredicate = (int denominator, int lambda_numerator, int mu_numerator) ->
                0 < mu_numerator &&
                        0 < lambda_numerator &&
                        mu_numerator + lambda_numerator < denominator;

        return solve(a, b, c, d, e, f, interiorPredicate);
    }

    private static boolean solve(int a, int b, int c, int d, int e, int f, Predicate p)
    {
        int denominator = d * b - a * e;

        if (denominator == 0)
        {
            return false;
        }
        else
        {
            int lambda_numerator = d * c - a * f;
            int mu_numerator = b * f - e * c;

            if (denominator < 0)
            {
                lambda_numerator = -lambda_numerator;
                mu_numerator = -mu_numerator;
                denominator = -denominator;
            }

            return p.predicate(denominator, lambda_numerator, mu_numerator);
        }
    }

    public boolean equals(Vertex v){
        return this.x==v.x&&this.y==v.y;
    }

    /**
     * Returns the string representation of the object
     * @return
     */
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    interface Predicate
    {
        boolean predicate(int denominator, int lambda_numerator, int mu_numerator);
    }

}
