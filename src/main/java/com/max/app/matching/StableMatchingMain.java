package com.max.app.matching;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class StableMatchingMain {

    public static void main(String[] args) throws Exception {

        Map<String, String[]> doctors = new HashMap<>();
        doctors.put("q", new String[]{"A", "B", "C", "D"});
        doctors.put("r", new String[]{"A", "D", "C", "B"});
        doctors.put("s", new String[]{"B", "A", "C", "D"});
        doctors.put("t", new String[]{"D", "B", "C", "A"});

        Map<String, String[]> hospitals = new HashMap<>();
        hospitals.put("A", new String[]{"t", "s", "r", "q"});
        hospitals.put("B", new String[]{"r", "t", "q", "s"});
        hospitals.put("C", new String[]{"t", "r", "s", "q"});
        hospitals.put("D", new String[]{"s", "r", "q", "t"});

        for (DoctorAndHospital singleMatch : findStableMatching(doctors, hospitals)) {
            System.out.println(singleMatch);
        }

        System.out.printf("StableMatchingMain completed. java version: %s%n", System.getProperty("java.version"));
    }

    /**
     * Stable matching algorithm implementation using Boston pool (Gale-Shapley) greedy approach.
     */
    public static List<DoctorAndHospital> findStableMatching(Map<String, String[]> doctors, Map<String, String[]> hospitals) {

        Queue<HospitalState> hospitalsQueue = new ArrayDeque<>();
        Map<String, HospitalState> hospitalNameToState = new HashMap<>();

        for (String singleHospitalName : hospitals.keySet()) {
            HospitalState state = new HospitalState(singleHospitalName);

            hospitalNameToState.put(singleHospitalName, state);
            hospitalsQueue.add(state);
        }

        Map<String, DoctorState> doctorMatchings = new HashMap<>();

        while (!hospitalsQueue.isEmpty()) {
            HospitalState unmatchedHospital = hospitalsQueue.poll();

            String[] curHospitalPrefs = hospitals.get(unmatchedHospital.name);

            for (int doctorIndex = unmatchedHospital.index; doctorIndex < curHospitalPrefs.length; ++doctorIndex) {
                String doctorToPropose = curHospitalPrefs[doctorIndex];

                Proposition proposition = propose(unmatchedHospital.name, doctorMatchings.get(doctorToPropose),
                                                  doctors.get(doctorToPropose));

                if (proposition.accepted) {
                    makeMatching(doctorMatchings, unmatchedHospital.name, doctorToPropose, doctors);
                    if( proposition.rejectedHospital != null ) {
                        addRejectedHospitalToQueue(hospitalNameToState.get(proposition.rejectedHospital),
                                                   proposition.rejectedHospital, hospitalsQueue);
                    }
                    break;
                }
            }
        }

        return combineAllMatchings(doctorMatchings);
    }

    private static Proposition propose(String hospital, DoctorState doctorState, String[] doctorPrefs){

        if( doctorState == null ){
            return Proposition.accept(null);
        }

        for(int hospitalIndex = doctorState.index; hospitalIndex >=0; --hospitalIndex){
            if( doctorPrefs[hospitalIndex].equals(hospital) ){
                return Proposition.accept(doctorState.acceptedHospital);
            }
        }


        return Proposition.reject();
    }

    private static void makeMatching(Map<String, DoctorState> doctorMatchings, String hospital,
                                     String doctor, Map<String, String[]> doctors) {

        String[] hospitalsForDoctor = doctors.get(doctor);

        if (doctorMatchings.containsKey(doctor)) {
            DoctorState state = doctorMatchings.get(doctor);
            state.acceptedHospital = hospital;
            state.index = findHospitalIndex(hospital, hospitalsForDoctor, state.index);
        }
        else {
            DoctorState state = new DoctorState(doctor, hospital, findHospitalIndex(hospital, hospitalsForDoctor,
                                                                            hospitalsForDoctor.length - 1));
            doctorMatchings.put(doctor, state);
        }

    }

    private static int findHospitalIndex(String hospitalToSearch, String[] hospitals, int searchStartIndex) {

        for (int i = searchStartIndex; i >= 0; --i) {
            if (hospitals[i].equals(hospitalToSearch)) {
                return i;
            }
        }

        throw new IllegalStateException("Can't find hospital: '" + hospitalToSearch + "' in hospitals: " +
                                                Arrays.asList(hospitals));
    }

    private static void addRejectedHospitalToQueue(HospitalState rejectedState, String rejectedHospital,
                                                   Queue<HospitalState> hospitalsQueue) {

        assert rejectedHospital != null : "'null' rejectedState detected for hospital name '" + rejectedHospital + "'";

        rejectedState.index += 1;

        hospitalsQueue.add(rejectedState);
    }

    private static List<DoctorAndHospital> combineAllMatchings(Map<String, DoctorState> matchings) {

        List<DoctorAndHospital> result = new ArrayList<>();
        for (Map.Entry<String, DoctorState> entry : matchings.entrySet()) {
            result.add(new DoctorAndHospital(entry.getKey(), entry.getValue().acceptedHospital));
        }

        return result;
    }

    static final class Proposition {
        final boolean accepted;
        final String rejectedHospital;

        static Proposition accept(String rejectedHospital){
            return new Proposition(true, rejectedHospital);
        }

        static Proposition reject(){
            return new Proposition(false, null);
        }

        private Proposition(boolean accepted, String rejectedHospital) {
            this.accepted = accepted;
            this.rejectedHospital = rejectedHospital;
        }

        @Override
        public String toString() {
            return String.format("accepted: %b, rejected hospital: %s", accepted, rejectedHospital);
        }
    }

    static final class DoctorAndHospital {
        final String doctor;
        final String hospital;

        DoctorAndHospital(String doctor, String hospital) {
            this.doctor = doctor;
            this.hospital = hospital;
        }

        @Override
        public String toString() {
            return String.format("doctor: %s, hospital: %s", doctor, hospital);
        }

    }

    static final class HospitalState {
        final String name;
        int index;

        HospitalState(String name) {
            this.name = name;
            this.index = 0;
        }

        @Override
        public String toString() {
            return String.format("%s: index: %d", name, index);
        }
    }

    static final class DoctorState {
        final String name;
        String acceptedHospital;
        int index;

        DoctorState(String name, String acceptedHospital, int index) {
            this.name = name;
            this.acceptedHospital = acceptedHospital;
            this.index = index;
        }

        @Override
        public String toString() {
            return String.format("hospital: %s, index: %d", acceptedHospital, index);
        }

    }

}
