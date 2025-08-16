package com.mycompany.mountainhiking.views;

import com.mycompany.mountainhiking.models.Mountain;
import com.mycompany.mountainhiking.models.Registration;

import java.text.DecimalFormat;
import java.util.*;

public class ConsoleView {
    private final Scanner sc = new Scanner(System.in);

    public String menu() {
        System.out.println("\n==== Mountain Hiking ====");
        System.out.println(""
                + "1) New\n"
                + "2) Update\n"
                + "3) Display\n"
                + "4) Delete\n"
                + "5) Search\n"
                + "6) Filter\n"
                + "7) Statistics\n"
                + "8) Save\n"
                + "9) Delete Data File\n"
                + "10) Exit\n");
        return ask("Select [1-10]: ").trim();
    }

    public String ask(String label) { System.out.print(label); return sc.nextLine(); }
    public boolean confirm(String q) { return ask(q + " (Y/N): ").trim().equalsIgnoreCase("Y"); }
    public void msg(String s) { System.out.println(s); }
    private String money(long v) { return new DecimalFormat("#,##0").format(v); }

    public void printAll(Collection<Registration> regs, Map<String, Mountain> mountains) {
        if (regs == null || regs.isEmpty()) { msg("No data."); return; }
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-20s | %-11s | %-8s | %-22s | %10s%n", "StudentID","Name","Phone","Campus","Mountain","Fee");
        System.out.println("--------------------------------------------------------------------------------------------------");
        regs.stream().sorted(Comparator.comparing(r -> r.student.getId())).forEach(r -> {
            Mountain m = mountains.get(r.mountainCode);
            String mName = m != null ? m.name : "?";
            System.out.printf("%-10s | %-20s | %-11s | %-8s | %-22s | %10s%n", r.student.getId(), r.student.getName(), r.student.getPhone(), r.student.campus(), mName, money(r.tuitionFee));
        });
    }

    public void listMountains(Map<String, Mountain> mountains) {
        System.out.println("Available mountains:");
        for (Mountain m : mountains.values()) System.out.print(m.code + ":" + m.name + "\n");
        System.out.println();
    }

    public void printStats(Map<String, Integer> cnt, Map<String, Long> sum, Map<String, Mountain> mountains) {
        System.out.printf("%-8s | %-22s | %6s | %12s%n", "Code", "Mountain", "Count", "Total Fee");
        System.out.println("----------------------------------------------------------------");
        for (String code : cnt.keySet()) {
            String name = mountains.get(code) != null ? mountains.get(code).name : "?";
            System.out.printf("%-8s | %-22s | %6d | %12s%n", code, name, cnt.get(code), money(sum.get(code)));
        }
    }
}
