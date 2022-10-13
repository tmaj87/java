package pl.tmaj;

import java.util.ArrayList;
import java.util.List;

public class IdGenerator {
    public List<String> getAllIds(String[] params, int limit) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int[] pointers = new int[params.length];
        generateIds(params, pointers, limit, sb, result);
        return result;
    }

    private void generateIds(String[] params, int[] pointers, int limit, StringBuilder sb, List<String> result) {
        if (limit == 0 || pointers[0] == params[0].length() - 1) {
            return;
        }
        sb.setLength(0);
        for (int i = 0; i < params.length; i++) {
            String append = params[i].substring(pointers[i], pointers[i] + 1);
            sb.append(append);
        }

        for (int i = params.length - 1; i >= 0; i--) {
            if (pointers[i] != params[i].length() - 1) {
                pointers[i]++;
                break;
            } else {
                pointers[i] = 0;
            }
        }

        result.add(sb.toString());
        generateIds(params, pointers, --limit, sb, result);
    }
}
