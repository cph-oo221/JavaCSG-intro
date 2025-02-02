package Car;

import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;

import java.util.ArrayList;
import java.util.List;

public class CarBody
{
    private final JavaCSG csg;
    private final double width;
    private final double height;
    private final double length;

    public CarBody(JavaCSG csg, double width, double height, double length)
    {
        this.csg = csg;
        this.width = width;
        this.height = height;
        this.length = length;
    }

    private  List<Geometry3D> wheelArches()
    {
        List<Geometry3D> wheelArches = new ArrayList<>();
        Geometry3D wheelArch = csg.cylinder3D(width / 2, height, 360, true);
        wheelArch = csg.rotate3DY(csg.degrees(90)).transform(wheelArch);

        for (int i = 0; i < 4; i++)
        {
            Geometry3D currentWheelArch = wheelArch;

            if (i == 0)
            {
                currentWheelArch = csg.translate3D(width / 1.4, height / 2, - height / 2.25).transform(currentWheelArch);
                wheelArches.add(currentWheelArch);
            }
            if (i == 1)
            {
                currentWheelArch = csg.translate3D(-width / 1.4, height / 2, - height / 2.25).transform(currentWheelArch);
                wheelArches.add(currentWheelArch);
            }
            if (i == 2)
            {
                currentWheelArch = csg.translate3D(-width / 1.4, -height / 2, - height / 2.25).transform(currentWheelArch);
                wheelArches.add(currentWheelArch);
            }
            if (i == 3)
            {
                currentWheelArch = csg.translate3D(width / 1.4 , -height / 2, - height / 2.25).transform(currentWheelArch);
                wheelArches.add(currentWheelArch);
            }
        }

        // ARCH HOLE FOR FRONT WHEELS
        Geometry3D frontWheelsArchHole = csg.cylinder3D(width / 10, height + 10, 360, true);
        frontWheelsArchHole = csg.rotate3DY(csg.degrees(90)).transform(frontWheelsArchHole);
        frontWheelsArchHole = csg.translate3D(0, height / 2, - (length / 5)).transform(frontWheelsArchHole);
        wheelArches.add(frontWheelsArchHole);

        // ARCH HOLE FOR BACK WHEELS
        Geometry3D backWheelsArchHole = csg.cylinder3D(width / 10, height + 10, 360, true);
        backWheelsArchHole = csg.rotate3DY(csg.degrees(90)).transform(backWheelsArchHole);
        backWheelsArchHole = csg.translate3D(0, -(height / 2), - (length / 5)).transform(backWheelsArchHole);
        wheelArches.add(backWheelsArchHole);

        return wheelArches;
    }

    private Geometry3D body()
    {
        List<Geometry3D> bodyParts = new ArrayList<>();

        // CAR BODY
        Geometry3D body = csg.box3D(width, height, length, true);
        body = csg.rotate3DX(csg.degrees(90)).transform(body);

        // FRONT WINDOW
        Geometry3D frontWindow = csg.box3D(width * 2, height / 1.2, length / 3, true);
        frontWindow = csg.translate3D(- width / 5, height, height / 2.2).transform(frontWindow);
        bodyParts.add(frontWindow);

        // BACK BUMPER
        Geometry3D backBumper = csg.box3D(width * 2, height / 1.2, length / 3, true);
        backBumper = csg.translate3D(- width / 5, - (height + 12), - height / 1.5).transform(backBumper);
        bodyParts.add(backBumper);

        return csg.difference3D(body, bodyParts);
    }


    // GENERATE CAR BODY
    public Geometry3D generate()
    {
        return csg.difference3D(body(), wheelArches());
    }
}
