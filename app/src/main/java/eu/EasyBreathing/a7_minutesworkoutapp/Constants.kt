package eu.EasyBreathing.a7_minutesworkoutapp

import java.util.*


// Clasă constantă unde se adăuga valorile constante ale proiectului.

class Constants {
    companion object {

        // Imaginile folosite aici sunt adăugate în folderul drawable.
        // Aici adăugăm toate exercițiile la o singură listă cu toate valorile implicite.

        fun defaultExerciseList(): ArrayList<ExerciseModel> {

            val exerciseList = ArrayList<ExerciseModel>()

            val yourback1 =
                ExerciseModel(1, " Inhale, exhale, lying on your back at 70 degrees. ",
                    R.drawable.ex_your_back1_,
                    false, false)
            exerciseList.add(yourback1)

            val yourback2 = ExerciseModel(2, " Inhale, exhale, lying on the left side, head on the pillow, the legs flexed and the table inclined to 30 degrees caudocranial. ",
                R.drawable.ex_your_back2_1,
                false, false)
            exerciseList.add(yourback2)

            val yourback3 = ExerciseModel(3, " Inhale, exhale, lying on the back, knees bent with the head on the pillow and the table inclined 30 degrees caudocranial. ",
                R.drawable.ex_your_back3_,
                false, false)
            exerciseList.add(yourback3)

            val yourback4 = ExerciseModel(4, " Inhale, exhale, lying on the left side, head on the pillow, the legs flexed, the left hand behind the back and the table inclined to 30 degrees caudocranial. ",
                    R.drawable.ex_your_back4_,
                    false, false)
            exerciseList.add(yourback4)

            val yourback5 = ExerciseModel(5, " Inhale, exhale, lying on the stomach, with a pillow under the stomach, legs outstretched, hands next to the body and the table inclined to 30 degrees caudocranial. ",
                    R.drawable.ex_your_back5_,
                    false, false)
            exerciseList.add(yourback5)

            val yourback6 = ExerciseModel(6, " Inhale, exhale, lying on the left side, with a pillow under the head, chest and abdomen, the right leg bent, the hands under the head and the table at 0 degrees. ",
                R.drawable.ex_your_back6_,
                false, false)
            exerciseList.add(yourback6)

            val yourback7 = ExerciseModel(7, " Inhale, exhale, lying on the right side, with a pillow under the head, chest and abdomen, the left leg bent, the hands under the head and the table at 0 degrees. ",
                    R.drawable.ex_your_back7_,
                    false, false)
            exerciseList.add(yourback7)

            val yourback8 = ExerciseModel(8, " Inhale, exhale, lying on the back, knees bent with the head on the pillow, hands next to the body and the table at 0 degrees. ",
                R.drawable.ex_your_back8_,
                false, false)
            exerciseList.add(yourback8)

            val yourback9 = ExerciseModel(9, " Inhale, exhale, lying on the stomach, with a pillow under the pelvis, legs outstretched, hands under the head and table at 0 degrees. ",
                    R.drawable.ex_your_back9_,
                    false, false)
            exerciseList.add(yourback9)

            val yourback10 = ExerciseModel(10, " Inhale, exhale, lying on the right side, with a pillow under the head, chest and abdomen, knees bent, hands next to the body and the table inclined at 30 degrees caudocranial. ",
                R.drawable.ex_your_back10_,
                false, false)
            exerciseList.add(yourback10)

            val yourback11 = ExerciseModel(11, " Inhale, exhale, lying on the right side, with a pillow under the head, knees bent, hands next to the body and the table inclined at 30 degrees caudocranial. ",
                    R.drawable.ex_your_back11_,
                    false, false)
            exerciseList.add(yourback11)

            val yourback12 = ExerciseModel(12, " Inhale, exhale, sitting on the edge of the table with a pillow in his arms, leaning over it with his hands over the pillows.",
                R.drawable.ex_your_back12_, false, false)
            exerciseList.add(yourback12)

            return exerciseList
        }
    }
}
