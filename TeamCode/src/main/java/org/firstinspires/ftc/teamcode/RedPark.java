package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
// Created  for 16887.
@Autonomous(name="RedParkTest", group="Simple")
//@Disabled
public class RedPark extends BaseRobot{
    private int step = 0;

    @Override
    public void init() {
        super.init();
        DEBUG = true;
    }

    @Override
    public void start() {
        super.start();
//        lift1.setPower(0.0);            // Don't need LIFT for this mode
//        lift1.setTargetPosition(0);
    }

    @Override
    public void loop() {
        if (time<3.25) {
            tank_drive(-0.5,-0.5);
            //      auto_drive(-0.3, 5);
            //   rotate1.setPower(-0.3);
        }
        //else if (time<5.1 && time>3.0)
        //  auto_turn(-0.5,7);
        //else if (time>5.1 && time <5.5) {
        //   topSpin.setPower(-0.5);
        //  rotate1.setPower(-0.5);
        // }

        //else if (time<6.2 && time > 5.5)
        //{
        //  tank_drive(-0.2,-0.2);
        //}
        else
        {

            tank_drive(0.0,0.0);
            topSpin.setPower(0);
            return;

        }
    /*    switch (step) {
            case 0:         auto_drive(-0.3, 27);
                step++;
                break;
            case 1: if (time<120)
                topSpin.setPower(0.7);   //   auto_drive(-0.3, 12);
                step++;
                break;
            case 2:   topSpin.setPower(0);
                auto_turn(-0.3, 180);
                step++;
                break;
           case 3:      //  auto_drive(0.5, 60);
            //    step++;
                break;

            default: break;
        }

     */
        if (DEBUG) {
            telemetry.addData("Front curr pos:", "Left=%d, Right=%d", get_leftFront_motor_enc(), get_rightFront_motor_enc());
            telemetry.addData("Back  curr pos:", "Left=%d, Right=%d", get_leftBack_motor_enc(), get_rightBack_motor_enc());
            telemetry.addData("Front power: ", "Left=%.2f, Right=%.2f", leftFront.getPower(), rightFront.getPower());
            telemetry.addData("Back  power: ", "Left=%.2f, Right=%.2f", leftBack.getPower(), rightBack.getPower());
            telemetry.addData("linearSlide:", "pos=%d, power=%.2f", get_linearSlide_motor_enc(), linearSlide.getPower());
            telemetry.addData("topSpin:", "pos=%d, power=%.2f", get_topSpin_motor_enc(), topSpin.getPower());
            telemetry.addData("rotate1:", "pos=%d, power=%.2f", get_rotate1_motor_enc(), rotate1.getPower());
        }
    }
}
