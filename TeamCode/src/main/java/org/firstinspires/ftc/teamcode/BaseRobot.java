package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; //could cause problems
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import static com.qualcomm.robotcore.hardware.DistanceSensor.distanceOutOfRange;

import com.qualcomm.robotcore.hardware.CRServo;

//created for 16887
//2021-2022 FTC Season
public class BaseRobot extends OpMode {
    public DcMotor leftBack, rightBack, leftFront, rightFront, linearSlide, actuator, axle_Spin; //rotate2;   // The four wheels
// public Servo top_spin;                                       // The top spinning wheel
      public Servo box_Spin;
      public CRServo topSpin; //box_Spin;
//    public ColorSensor front_sensor;
//    public DistanceSensor distance_sensor;
    public ElapsedTime timer = new ElapsedTime();

    public boolean DEBUG=false;                     // Debug flag
//    public Boolean autonomous = false;

    //public boolean DEBUG = true;                     // Debug flag = false
//    public Boolean autonmous = false;


    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        axle_Spin = hardwareMap.get(DcMotor.class, "axle_Spin");
        linearSlide = hardwareMap.get(DcMotor.class, "linearSlide");
        actuator = hardwareMap.get(DcMotor.class, "actuator");
       // rotate2 = hardwareMap.get(DcMotor.class, "rotate2");

        //box_Spin = hardwareMap.get(CRServo.class, "box_Spin");
       box_Spin = hardwareMap.get(Servo.class, "box_Spin");
        topSpin = hardwareMap.get(CRServo.class, "topSpin");

        //intake  = hardwareMap.get(DcMotor.class, "intake");

//        front_sensor = hardwareMap.get(ColorSensor.class, "front_sensor");
//        distance_sensor = hardwareMap.get(DistanceSensor.class, "front_sensor");
//        front_sensor.enableLed(false);               // turn off the sensor LED to save power

        // ZeroPowerBehavior of the motors
        telemetry.addData("INI Front ZeroP behavior:", "Left=%s, Right=%s", leftFront.getZeroPowerBehavior(), rightFront.getZeroPowerBehavior());
        telemetry.addData("INI Back ZeroP behavior: ", "Left=%s, Right=%s", leftBack.getZeroPowerBehavior(), rightBack.getZeroPowerBehavior());
        //telemetry.addData("INI topSpin: ", "ZeroP=%s, POS=%d", topSpin.getZeroPowerBehavior(), topSpin.getCurrentPosition());

        telemetry.addData("INI linearSlide: ", "ZeroP=%s, POS=%d", linearSlide.getZeroPowerBehavior(), linearSlide.getCurrentPosition());

        telemetry.addData("INI actuator: ", "ZeroP=%s, POS=%d", actuator.getZeroPowerBehavior(), actuator.getCurrentPosition());
     //   telemetry.addData("INI rotate2: ", "ZeroP=%s, POS=%d", rotate2.getZeroPowerBehavior(), rotate2.getCurrentPosition());
        //telemetry.addData("INI rotate2: ", "ZeroP=%s, POS=%d", intake.getZeroPowerBehavior(), intake.getCurrentPosition());
//        telemetry.addData("INI SPIN ZeroP behavior: ", "spin1=%s, spin2=%s", spin1.getZeroPowerBehavior(), spin2.getZeroPowerBehavior());
//        telemetry.addData("INI Sen: ", "%d/ %d/ %d/ %d/ %d", front_sensor.alpha(), front_sensor.red(), front_sensor.green(), front_sensor.blue(), front_sensor.argb());
//        telemetry.addData("INI Distance (cm)", distance_sensor.getDistance(DistanceUnit.CM));

        telemetry.addData("INI Servo dir: ",  "Box=%s", box_Spin.getDirection());
    //    telemetry.addData("Box Power", "=%.2f", box_Spin.getPower());

        // BRAKE: The motor stops and then brakes, actively resisting any external force which attempts to turn the motor.
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //topSpin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        linearSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        actuator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
      //  rotate2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        lift1.setDirection(DcMotorSimple.Direction.REVERSE);        // Because of the way the motor is mounted, this will avoid using negative numbers
//        lift1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //spin1.setDirection(DcMotorSimple.Direction.REVERSE);        // Because of the way the motor is mounted, this will avoid using negative numbers
//        spin1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //spin2.setDirection(DcMotorSimple.Direction.REVERSE);        // Because of the way the motor is mounted, this will avoid using negative numbers
//        spin2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        // Positive is UP and negative in down.  It resets to zero at start().
    }

    @Override
    public void start() {
        timer.reset();
        reset_drive_encoders();         // reset all  motors: set encoders to zero and set modes
        reset_rotate_encoders();
        reset_linearSlide_encoders();
        //reset_spin_encoders();
//        reset_lift1_encoder();          // reset lift motor: set encoders to zero and set modes
//        reset_spin1_encoder();
//        reset_spin2_encoder();
        //open_lift1();                  // open the left and right servos
//set_lift1_target_pos(ConstantVariables.K_LIFT_UP);
//        front_sensor.enableLed(true);   // turn on the sensor LED
        telemetry.addData("START Front ZeroP behavior:", "Left=%s, Right=%s", leftFront.getZeroPowerBehavior(), rightFront.getZeroPowerBehavior());
        telemetry.addData("START Back ZeroP behavior: ", "Left=%s, Right=%s", leftBack.getZeroPowerBehavior(), rightBack.getZeroPowerBehavior());

        telemetry.addData("START linearSlide ZeroP behavior: ", "linearSlide=%s", linearSlide.getZeroPowerBehavior());
        //telemetry.addData("START topSpin ZeroP behavior: ", "topSpin=%s", topSpin.getZeroPowerBehavior());

   //     telemetry.addData("START rotate ZeroP behavior: ", "actuator=%s", actuator.getZeroPowerBehavior());
     //   telemetry.addData("START rotate ZeroP behavior: ", "rotate2=%s", rotate2.getZeroPowerBehavior());
        //telemetry.addData("START rotate ZeroP behavior: ", "rotate2=%s", intake.getZeroPowerBehavior());
//        telemetry.addData("START LIFT1 position", lift1.getCurrentPosition());
//        telemetry.addData("START Sen: ", "%d/ %d/ %d/ %d/ %d", front_sensor.alpha(), front_sensor.red(), front_sensor.green(), front_sensor.blue(), front_sensor.argb());
//        telemetry.addData("START Distance (cm)", distance_sensor.getDistance(DistanceUnit.CM));
  //      telemetry.addData("START Servo dir: ", "Dir=%s, RIGHT=%s", box_Spin.getDirection());

    }

    @Override
    public void stop() {
//        front_sensor.enableLed(false);   // turn off the sensor LED to save power
//        reset_drive_encoders();         // reset all  motors: set encoders to zero and set modes
//        reset_rotate_encoders();
//        reset_linearSlide_encoders();
//        reset_spin_encoders();
        telemetry.addData("Front curr pos:", "Left=%d, Right=%d", get_leftFront_motor_enc(), get_rightFront_motor_enc());
        telemetry.addData("Back  curr pos:", "Left=%d, Right=%d", get_leftBack_motor_enc(), get_rightBack_motor_enc());
        telemetry.addData("Front power: ", "Left=%.2f, Right=%.2f", leftFront.getPower(), rightFront.getPower());
        telemetry.addData("Back  power: ", "Left=%.2f, Right=%.2f", leftBack.getPower(), rightBack.getPower());
        telemetry.addData("linearSlide:", "pos=%d, power=%.2f", get_linearSlide_motor_enc(), linearSlide.getPower());
        //telemetry.addData("topSpin:", "pos=%d, power=%.2f", get_topSpin_motor_enc(), topSpin.getPower());
        telemetry.addData("actuator:", "pos=%d, power=%.2f", get_actuator_motor_enc(), actuator.getPower());
     //   telemetry.addData("rotate2:", "pos=%d, power=%.2f", get_rotate2_motor_enc(), rotate2.getPower());
    }

    @Override
    public void loop() {
        if (DEBUG) {
//            String detected_color = "";
//            if (is_black(front_sensor.alpha(), front_sensor.red(), front_sensor.blue())) detected_color = detected_color + " Black ";
//            if (is_yellow(front_sensor.alpha(), front_sensor.red(), front_sensor.green(), front_sensor.blue())) detected_color = detected_color + " Yellow ";
//            telemetry.addData("Timer ", "%.1f", timer.seconds());
//            telemetry.addData("LIFT1 motor current pos: ", "%d, Color = %s", get_lift1_motor_enc(), detected_color);
            telemetry.addData("Front curr pos:", "Left=%d, Right=%d", get_leftFront_motor_enc(), get_rightFront_motor_enc());
            telemetry.addData("Back  curr pos:", "Left=%d, Right=%d", get_leftBack_motor_enc(), get_rightBack_motor_enc());
            telemetry.addData("Front power: ", "Left=%.2f, Right=%.2f", leftFront.getPower(), rightFront.getPower());
            telemetry.addData("Back  power: ", "Left=%.2f, Right=%.2f", leftBack.getPower(), rightBack.getPower());
            telemetry.addData("linearSlide:", "pos=%d, power=%.2f", get_linearSlide_motor_enc(), linearSlide.getPower());
            //telemetry.addData("topSpin:", "pos=%d, power=%.2f", get_topSpin_motor_enc(), topSpin.getPower());
            telemetry.addData("actuator:", "pos=%d, power=%.2f", get_actuator_motor_enc(), actuator.getPower());
     //       telemetry.addData("rotate2:", "pos=%d, power=%.2f", get_rotate2_motor_enc(), rotate2.getPower());

            // telemetry.addData("intake pos:", "rotate2 =%d", get_intake_motor_enc());
            // telemetry.addData("intake  power: ", "intake =%.2f", intake.getPower());
        //    telemetry.addData("Box_Servo pos: ", "=%.2f", box_Spin.get());
//            telemetry.addData("Sen: ", " %d/ %d/ %d/ %d/ %d", front_sensor.alpha(), front_sensor.red(), front_sensor.green(), front_sensor.blue(), front_sensor.argb());
//            telemetry.addData("Red：", front_sensor.red());
//            telemetry.addData("Green：", front_sensor.green());
//            telemetry.addData("Blue：", front_sensor.blue());
//            double detected_dist = distance_sensor.getDistance(DistanceUnit.CM);
//            if (detected_dist == distanceOutOfRange) {
//                telemetry.addData("Distance: out of range", distance_sensor.getDistance(DistanceUnit.CM));
//            } else {
//               telemetry.addData("Distance: ", "%.2fcm", distance_sensor.getDistance(DistanceUnit.CM));
//            }
        }
    }
    /* @param power:   the speed to turn at. Negative for reverse
     * @param dist_inches:  move "inches" inches.  "inches" positive for forward
     * @return Whether the target "inches" has been reached.
     */
    // Complicated:  watch out for the +/- signs
    // The right pos is used as the measure
    public boolean auto_drive(double power, double dist_inch) {
        int CURR_ENC = get_rightFront_motor_enc();  // the current position
        int TARGET_ENC;
        double left_speed = power;                 // Left motors are running in reverse direction
        double right_speed = -power;               // Right motors are running in reverse direction

        left_speed = Range.clip(left_speed, -1, 1);
        right_speed = Range.clip(right_speed, -1, 1);
        leftFront.setPower(left_speed);
        leftBack.setPower(-left_speed);             // Back is the opposite of Front
        rightFront.setPower(right_speed);
        rightBack.setPower(-right_speed);           // Back is the opposite of Front
//        Improvement for accuracy
//        double error = -get_leftFront_motor_enc() - get_rightFront_motor_enc();
//        error /= ConstantVariables.K_DRIVE_ERROR_P;
//        left_speed += error; right_speed -= error;
        // Right motor is running in reverse direction
        if (power < 0.0) {  // Right pos is increasing
            // Move in opposite direction
            TARGET_ENC = (int) (ConstantVariables.K_PPIN_DRIVE * dist_inch * 0.9 + 0.5); // inches to turns
        //    if (DEBUG) telemetry.addData("Auto_D: ", "Now %d/Tar %.2f", get_rightFront_motor_enc(), TARGET_ENC);
            while (get_rightFront_motor_enc() < (CURR_ENC + TARGET_ENC)) {
                if (DEBUG) telemetry.addData("AUTO_DR:", "%d", get_rightFront_motor_enc());
            }
        }
        else {        // Right pos is decreasing
            TARGET_ENC = -(int) (ConstantVariables.K_PPIN_DRIVE * dist_inch * 0.9 + 0.5); // inches to turns NEGATIVE
        //    if (DEBUG) telemetry.addData("Auto_D: ", "Now %d/Tar %.2f", get_rightFront_motor_enc(), TARGET_ENC);
            while (get_rightFront_motor_enc() > (CURR_ENC + TARGET_ENC)) {
                if (DEBUG) telemetry.addData("AUTO_DR:", "%d", get_rightFront_motor_enc());
            }
        }
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        return true;
//        TARGET_ENC = (int) (ConstantVariables.K_PPIN_DRIVE * dist_inch * 1.5);
//        if (Math.abs(get_rightFront_motor_enc()) >= TARGET_ENC) { // Arrived at target destination
//            return true;
//        }
    }
        /* @param power:   the speed to turn at. Negative for left.
         * @param degrees: the number of degrees to turn. ALWAYS POSITIVE
         * @return Whether the target angle has been reached.
         */
        // Use the right front encoder as a measure of the amount of turn
        // The right front motor is mounted in reverse!!!
    public boolean auto_turn(double power, double degrees) {
        int CURR_ENC = get_rightFront_motor_enc();
        int TARGET_ENC;
//        double TARGET_ENC = Math.abs(ConstantVariables.K_PPDEG_DRIVE * degrees * 1.0);  // degrees to turns
        double speed = Range.clip(power, -1, 1);
//        if (DEBUG) telemetry.addData("AUTO_TU:", "Curr=%d, Tar=%d", CURR_ENC, TARGET_ENC);
        leftFront.setPower(-speed);
        leftBack.setPower(speed);          // Back is the opposite of Front
        rightFront.setPower(-speed);         // Right is the same direction of Left which means turns in opposite direction
        rightBack.setPower(speed);         // Back is the opposite of Front
        // Right front motor is running in opposite direction
        if (power < 0.0) {                    // Turning left -> right pos is decreasing
            TARGET_ENC = (int) (ConstantVariables.K_PPDEG_DRIVE * degrees*1.4 +0.5);  // degrees to turns NEGATIVE *1.2 +0.5
            while (get_rightFront_motor_enc() < (CURR_ENC + TARGET_ENC)) {//>    -
                if (DEBUG) telemetry.addData("AUTO_TU POS:", "%d", get_rightFront_motor_enc());
            }
        }
        else {                            // Turning right -> right pos is increasing
            TARGET_ENC = -(int) (ConstantVariables.K_PPDEG_DRIVE * degrees*1.4 +0.5);  // degrees to turns *1.1 +0.5
            while (get_rightFront_motor_enc() > (CURR_ENC + TARGET_ENC)) {//<
                if (DEBUG) telemetry.addData("AUTO_TU POS:", "%d", get_rightFront_motor_enc());
            }
        }
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        return true;
    }
//        if (get_rightFront_motor_enc() == (CURR_ENC + TARGET_ENC)) {  // get encoder = the difference
//            return true;
//        } else {
//        }
//    }

    // Positive for right, negative for left
    // Convert from inches to number of ticks per revolution
   public boolean auto_mecanum(double power, double inches) {
  //     rightFront.setDirection(DcMotor.Direction.REVERSE); these two will cause bot to turn
  //    leftFront.setDirection(DcMotor.Direction.REVERSE);
       // double TARGET_ENC = ConstantVariables.K_PPIN_DRIVE * inches;

       int CURR_ENC = get_rightFront_motor_enc();
       int TARGET_ENC;

   /*
        double leftFrontPower = Range.clip(0 - power, -1.0, 1.0);
        double leftBackPower = Range.clip(0 + power, -1.0, 1.0);
        double rightFrontPower = Range.clip(0 - power, -1.0, 1.0);
        double rightBackPower = Range.clip(0 + power, -1.0, 1.0);
*/
       double speed = power;
       speed = Range.clip(speed, -1, 1);
        leftFront.setPower(speed);//-
        leftBack.setPower(speed);
        rightFront.setPower(speed);//-
        rightBack.setPower(speed);
     //   if (DEBUG) telemetry.addData("MEC - Target_enc: ", "%.2f", TARGET_ENC);

       /*
        telemetry.addData("lf","=%.2f",leftFrontPower);
       telemetry.addData("rf","=%.2f",leftFrontPower);
       telemetry.addData("lb","=%.2f",leftFrontPower);
       telemetry.addData("rb","=%.2f",leftFrontPower);
       */

       if (power < 0.0) {  // Right pos is increasing
           // Move in opposite direction
           TARGET_ENC = (int) (ConstantVariables.K_PPIN_DRIVE * inches * 0.9 + 0.5); // inches to turns
     //      if (DEBUG) telemetry.addData("Auto_D: ", "Now %d/Tar %.2f", get_rightFront_motor_enc(), TARGET_ENC);

           while (get_rightFront_motor_enc() < (CURR_ENC + TARGET_ENC)) {//wheel turns in (neg)
               //<
               if (DEBUG) telemetry.addData("AUTO_MEC:", "%d", get_rightFront_motor_enc());
           }
       }
       else {        // Right pos is decreasing

           TARGET_ENC = -(int) (ConstantVariables.K_PPIN_DRIVE * inches * 0.9 + 0.5); // inches to turns NEGATIVE
       //    if (DEBUG) telemetry.addData("Auto_D: ", "Now %d/Tar %.2f", get_rightFront_motor_enc(), TARGET_ENC);
            //wheel turns out (pos)
           while (get_rightFront_motor_enc() > (CURR_ENC + TARGET_ENC)) {
               //>
               if (DEBUG) telemetry.addData("AUTO_MEC:", "%d", get_rightFront_motor_enc());
           }
       }


       leftFront.setPower(0);
       leftBack.setPower(0);
       rightFront.setPower(0);
       rightBack.setPower(0);
       return true;


/*
        if (Math.abs(get_rightFront_motor_enc()) >= TARGET_ENC) {
            leftFront.setPower(0);
            leftBack.setPower(0);
            rightFront.setPower(0);
            rightBack.setPower(0);
            return true;
        }


        else {
            //try {
              //  servo.setPower(power);

                //  Thread.sleep(100);
              //  wait(5000);
            //} catch (InterruptedException e) {
          //      e.printStackTrace();
        //    }
            return false;
        }

 */
    }


    public void tankanum_original(double rightPwr, double leftPwr, double lateralpwr) {
        rightPwr *= -1;

        double leftFrontPower = Range.clip(leftPwr - lateralpwr, -1.0, 1.0);
        double leftBackPower = Range.clip(leftPwr + lateralpwr, -1.0, 1.0);
        double rightFrontPower = Range.clip(rightPwr - lateralpwr, -1.0, 1.0);
        double rightBackPower = Range.clip(rightPwr + lateralpwr, -1.0, 1.0);

        leftFront.setPower(leftFrontPower);
        leftBack.setPower(-leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(-rightBackPower);
    }

    // lateralpwr: pos for right, neg for left
    // When strafing, rightPwr and leftPwr is assumed to be zero
    // With the change, there is no diagonal movement
    public void tankanum_drive(double rightPwr, double leftPwr, double lateralpwr) {
        rightPwr *= -1;                                 // rightPwr is in reverse
        double leftFrontPower, leftBackPower, rightFrontPower, rightBackPower;
        // When lateralpwr is very small, the robot moves purely forward or backward
        if (lateralpwr > -0.05 && lateralpwr < 0.05) {  // Purely forward or backward
            leftFrontPower = leftPwr;
            leftBackPower = leftPwr;
            rightFrontPower = rightPwr;                 // left = right's opposite
            rightBackPower = rightPwr;
        } else {                                        // Strafe
            leftFrontPower = -lateralpwr;              // leftFront = rightBack's opposite
            leftBackPower = lateralpwr;               // leftBack  = rightFront's opposite
            rightFrontPower = -lateralpwr;
            rightBackPower = lateralpwr;
        }
// to adjust the powe r among the motors so that they have almost equal ACTUAL PHYSICAL powers
        leftFrontPower = Range.clip(leftFrontPower * ConstantVariables.K_LF_ADJUST, -1.0, 1.0);
        leftBackPower = Range.clip(leftBackPower * ConstantVariables.K_LB_ADJUST, -1.0, 1.0);
        rightFrontPower = Range.clip(rightFrontPower * ConstantVariables.K_RF_ADJUST, -1.0, 1.0);
        rightBackPower = Range.clip(rightBackPower * ConstantVariables.K_RB_ADJUST, -1.0, 1.0);

        leftFront.setPower(leftFrontPower);
        leftBack.setPower(leftBackPower);
        rightFront.setPower(rightFrontPower);
        rightBack.setPower(rightBackPower);
       // if (DEBUG) telemetry.addData("TANK- Lateral: ", "=%.2f",lateralpwr);
    }

    public void tank_drive(double leftPwr, double rightPwr) {
        rightPwr *= -1;                     // rightPwr is in reverse
        double leftPower = Range.clip(leftPwr, -0.6, 0.6);
        double rightPower = Range.clip(rightPwr, -0.6, 0.6);
        // Adjustment due to variation in physical motor power
        //   LF_Power = Range.clip(leftFrontPower * ConstantVariables.K_LF_ADJUST, -1.0, 1.0);
        //   RF_Power = Range.clip(rightBackPower * ConstantVariables.K_RB_ADJUST, -1.0, 1.0);
        //   LB_Power = Range.clip(leftFrontPower * ConstantVariables.K_LB_ADJUST, -1.0, 1.0);
        //   RB_Power = Range.clip(rightBackPower * ConstantVariables.K_RB_ADJUST, -1.0, 1.0);

        leftFront.setPower(-leftPower);//+
        leftBack.setPower(leftPower);   //-   // back is opposite to front?
        rightFront.setPower(-rightPower);  //+  // right is opposite to left
        rightBack.setPower(rightPower);  //-  // back is opposite to front?
        if (DEBUG)
            telemetry.addData("TANK-Power: ", "Left=%.2f, Right=%.2f", leftPower, rightPower);
    }

    public static void linearSlideSetPosition(DcMotor motor, int targetPosition) {
        double power = 0.5;
        //  Assuming postition is always positive?
        if (motor.getCurrentPosition() > targetPosition) power = -power;  // Reverse direction

        motor.setTargetPosition(targetPosition);  // Position has to be set before set mode
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motor.setTargetPosition(targetPosition);
//        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motor.setPower(power);
        while (motor.isBusy()) {
            // Need improvement, motor may be stuck
        }
        motor.setPower(0);
        return;
    }
    public boolean auto_spin(CRServo servo, double power, int spinTime) {
        servo.setPower(power);
        while (time<spinTime+1) {

            telemetry.addData("power", "=%.2f", power);
        }
          /*
            try {
            servo.setPower(power);

            //  Thread.sleep(100);
            wait(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */
        servo.setPower(0);
        return true;
    }

    // alpha is light intensity
//    public boolean is_black(int alpha, int red, int blue) { return ((alpha > 350) && (blue > red*(3.0/4.0))); }
//    public boolean is_yellow(int alpha, int red, int green, int blue) { return ((alpha > 350) && (red > 2*blue) && (green > 2*blue)); }
//    public boolean is_black(int alpha, int red, int blue) { return ((blue > red*(3.0/4.0))); }//   public boolean is_yellow(int alpha, int red, int green, int blue) { return ((red > 2*blue) && (green > 2*blue)); }

    //    public boolean set_lift1_target_pos(int target_pos) {
//        if (lift1.getCurrentPosition() == target_pos) return true;
//        lift1.setTargetPosition(target_pos);
//        lift1.setPower(ConstantVariables.K_LIFT_MAX_PWR);
//        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);  // Assume that the motor was reset.  Before setting the mode, the position has to be set correctly.
//        return(Math.abs(lift1.getCurrentPosition() - target_pos) < ConstantVariables.K_LIFT_ERROR);
//    }
    // get lift encoder
//    public int get_lift1_motor_enc() {      // RUN_TO_POSITION is more accurate
//        if (lift1.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
//            lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        }
//        return lift1.getCurrentPosition();
//    }
    public void reset_drive_encoders() {
        // The motor is to set the current encoder position to zero.
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // The motor is to do its best to run at targeted velocity.
//        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
// The motor is simply to run at whatever velocity is achieved by apply a particular power level to the motor.
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void reset_linearSlide_encoders() {
        // The motor is to set the current encoder position to zero.
        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // The motor is simply to run at whatever velocity is achieved by apply a particular power level to the motor.
        linearSlide.setPower(0.0);
        linearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    /*public void reset_spin_encoders() {
        // The motor is to set the current encoder position to zero.
        topSpin.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // The motor is simply to run at whatever velocity is achieved by apply a particular power level to the motor.
        topSpin.setPower(0.0);
        topSpin.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }*/
    public void reset_rotate_encoders() {
        // The motor is to set the current encoder position to zero.
        actuator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
   //     rotate2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // The motor is simply to run at whatever velocity is achieved by apply a particular power level to the motor.
        actuator.setPower(0.0);
     //   rotate2.setPower(0.0);
        actuator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
       // rotate2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /*    public void reset_lift1_encoder() {
            lift1.setTargetPosition(0);                        // Set to zero before RUN_TO_POSITION
            lift1.setPower(0.5*ConstantVariables.K_LIFT_MAX_PWR);  // Half power is sufficient
            lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // The motor is to attempt to rotate in whatever direction is necessary to cause
        // the encoder reading to advance or retreat from its current setting to the setting
        // which has been provided through the setTargetPosition() method.
    //        lift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
     */
//    public void reset_spin1_encoder() {
    // The motor is to set the current encoder position to zero.
//        spin1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        spin1.setPower(ConstantVariables.K_LIFT_MAX_PWR);
//        spin1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//    }
    //get leftBack encoder
    public int get_leftBack_motor_enc() {
//        if (leftBack.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
//            leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        }
        return leftBack.getCurrentPosition();
    }

    //get leftFront encoder
    public int get_leftFront_motor_enc() {
        return leftFront.getCurrentPosition();
    }

    //get rightBack encoder
    public int get_rightBack_motor_enc() {
        return rightBack.getCurrentPosition();
    }

    //get rightFront encoder
    public int get_rightFront_motor_enc() {
        return rightFront.getCurrentPosition();
    }

    public int get_linearSlide_motor_enc() {
        return linearSlide.getCurrentPosition();
    }

    //public int get_topSpin_motor_enc() {
        //return topSpin.getCurrentPosition();
    //}

    public int get_actuator_motor_enc() {
        return actuator.getCurrentPosition();
    }
    //public int get_rotate2_motor_enc() {
      //  return rotate2.getCurrentPosition();
    //}
}
