
package acme.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import acme.entities.audit.Mark;

public class MarkUtils {

	public static Mark getNewMark(final Collection<Mark> marksOfAudit) {
		Mark finalMark;
		if (!marksOfAudit.isEmpty()) {
			Map<Mark, Long> marksOfRecords;
			marksOfRecords = marksOfAudit.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			finalMark = marksOfRecords.entrySet().stream().max(Comparator.comparing(Entry::getValue)).get().getKey();
		} else
			finalMark = Mark.A;
		return finalMark;
	}

	public static Mark getMarkFromStringValue(final String s) {
		return Arrays.stream(Mark.values()).filter(m -> m.value.equals(s)).findFirst().orElse(null);
	}
}
