/*
 * Copyright (c) 2017-2020, guanquan.wang@yandex.com All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ttzero.excel.reader;

import org.junit.Test;

import static org.ttzero.excel.Print.println;
import static org.ttzero.excel.reader.Grid.FastGrid.isPowerOfTwo;

/**
 * @author guanquan.wang at 2020-01-09 17:19
 */
public class GridTest {
    @Test public void testGridType() {
        Grid grid = GridFactory.create(Dimension.of("A1:BM10"));

        assert grid instanceof Grid.IndexGrid;

        grid = GridFactory.create(Dimension.of("A1:B16383"));
        assert grid instanceof Grid.FastGrid;

        grid = GridFactory.create(Dimension.of("A1:B16384"));
        assert grid instanceof Grid.FractureGrid;

    }

    @Test public void testGrid1() {
        Grid grid = GridFactory.create(new Dimension(1, (short) 1, 10, (short) 1));

        grid.mark(new Dimension(3, (short) 1, 7, (short) 1));

        println(grid);

        assert !grid.test(2, 1);
        assert grid.test(3, 1);
        assert grid.test(7, 1);
        assert !grid.test(8, 1);
    }

    @Test public void testGrid2() {
        Grid grid = GridFactory.create(new Dimension(1, (short) 1, 10, (short) 2));

        grid.mark(new Dimension(3, (short) 1, 7, (short) 1));

        println(grid);

        assert !grid.test(2, 1);
        assert grid.test(3, 1);
        assert grid.test(7, 1);
        assert !grid.test(8, 1);
    }

    @Test public void testGrid4() {
        Grid grid = GridFactory.create(new Dimension(1, (short) 1, 10, (short) 3));

        grid.mark(new Dimension(3, (short) 1, 7, (short) 3));

        println(grid);

        assert !grid.test(2, 2);
        assert grid.test(3, 2);
        assert grid.test(3, 3);
        assert grid.test(7, 1);
        assert grid.test(7, 2);
        assert !grid.test(8, 2);
    }

    @Test public void testGrid8() {
        Grid grid = GridFactory.create(new Dimension(1, (short) 1, 10, (short) 8));

        grid.mark(new Dimension(3, (short) 4, 7, (short) 4));

        println(grid);
    }

    @Test public void testGrid8_2() {
        Grid grid = GridFactory.create(Dimension.of("A1:H71"));

        grid.mark(Dimension.of("C10:D10"));
        grid.mark(Dimension.of("C5:D5"));
        grid.mark(Dimension.of("C6:D6"));
        grid.mark(Dimension.of("C7:D7"));
        grid.mark(Dimension.of("C8:D8"));
        grid.mark(Dimension.of("C9:D9"));
        grid.mark(Dimension.of("A39:A71"));
        grid.mark(Dimension.of("D1:E1"));
        grid.mark(Dimension.of("A1:A26"));
        grid.mark(Dimension.of("A27:A38"));
        grid.mark(Dimension.of("E20:H20"));
        grid.mark(Dimension.of("E21:H21"));
        grid.mark(Dimension.of("E22:H22"));
        grid.mark(Dimension.of("E23:H23"));
        grid.mark(Dimension.of("E24:H24"));
        grid.mark(Dimension.of("E25:H25"));
        grid.mark(Dimension.of("C11:D11"));
        grid.mark(Dimension.of("C12:D12"));
        grid.mark(Dimension.of("C13:D13"));
        grid.mark(Dimension.of("C14:D14"));
        grid.mark(Dimension.of("C15:D15"));
        grid.mark(Dimension.of("C16:D16"));

        assert grid.toString().equals("FastGrid\n" +
            "00011001\n00000001\n00000001\n00000001\n" +
            "00001101\n00001101\n00001101\n00001101\n" +
            "00001101\n00001101\n00001101\n00001101\n" +
            "00001101\n00001101\n00001101\n00001101\n" +
            "00000001\n00000001\n00000001\n11110001\n" +
            "11110001\n11110001\n11110001\n11110001\n" +
            "11110001\n00000001\n00000001\n00000001\n" +
            "00000001\n00000001\n00000001\n00000001\n" +
            "00000001\n00000001\n00000001\n00000001\n" +
            "00000001\n00000001\n00000001\n00000001\n" +
            "00000001\n00000001\n00000001\n00000001\n" +
            "00000001\n00000001\n00000001\n00000001\n" +
            "00000001\n00000001\n00000001\n00000001\n" +
            "00000001\n00000001\n00000001\n00000001\n" +
            "00000001\n00000001\n00000001\n00000001\n" +
            "00000001\n00000001\n00000001\n00000001\n" +
            "00000001\n00000001\n00000001\n00000001\n" +
            "00000001\n00000001\n00000001");
    }

    @Test public void testGrid16() {
        Grid grid = GridFactory.create(new Dimension(1, (short) 1, 10, (short) 10));

        grid.mark(new Dimension(4, (short) 5, 9, (short) 7));

        println(grid);
    }

    @Test public void testGrid162() {
        Grid grid = GridFactory.create(new Dimension(1, (short) 1, 10, (short) 10));

        grid.mark(new Dimension(2, (short) 2, 4, (short) 6));
        grid.mark(new Dimension(3, (short) 7, 5, (short) 9));
        grid.mark(new Dimension(7, (short) 10, 10, (short) 10));

        println(grid);

        assert grid.test(7,10);
        assert !grid.test(6, 10);
        assert !grid.test(7, 9);
        assert grid.test(5, 8);
    }

    @Test public void testGrid32() {
        Grid grid = GridFactory.create(Dimension.of("A1:AF10"));

        grid.mark(Dimension.of("G3:AA9"));

        println(grid);
    }

    @Test public void testGrid64() {
        Grid grid = GridFactory.create(new Dimension(1, (short) 1, 10, (short) 54));

        grid.mark(new Dimension(2, (short) 2, 4, (short) 6));

        println(grid);
    }

    @Test public void testPowerOfTwo() {
        assert isPowerOfTwo(1);
        assert isPowerOfTwo(2);
        assert isPowerOfTwo(1024);
        assert !isPowerOfTwo(3);
        assert !isPowerOfTwo(6);
    }

    @Test public void testLinkedScanner() {
        Grid.Scanner scanner = new Grid.LinkedScanner();
        scanner.put(new Grid.LinkedScanner.E(Dimension.of("E5:F8"), null));
        scanner.put(new Grid.LinkedScanner.E(Dimension.of("B2:C2"), null));
        scanner.put(new Grid.LinkedScanner.E(Dimension.of("B16:E17"), null));
        scanner.put(new Grid.LinkedScanner.E(Dimension.of("A13:A20"), null));

        // Test iterator
        for (Grid.Scanner.Entry entry : scanner) {
            println(entry.getDim());
        }

        assert "B2:C2->E5:F8->A13:A20->B16:E17".equals(scanner.toString());

        scanner.get(5, 5);
        assert "E5:F8->B2:C2->A13:A20->B16:E17".equals(scanner.toString());

        scanner.get(5, 6);
        scanner.get(6, 5);
        scanner.get(6, 6);
        scanner.get(7, 5);
        scanner.get(7, 6);
        scanner.get(8, 5);
        scanner.get(8, 6);

        assert "B2:C2->A13:A20->B16:E17->E5:F8".equals(scanner.toString());
    }

    @Test public void testIndexGrid() {
        Grid grid = GridFactory.create(Dimension.of("A1:BM10"));

        grid.mark(Dimension.of("B2:E2"));

        assert !grid.test(1, 1);
        assert grid.test(2, 2);
        assert grid.test(2, 5);
        assert !grid.test(3, 3);
        assert !grid.test(6, 2);
    }

    @Test public void testFractureGrid() {
        Grid grid = GridFactory.create(Dimension.of("A1:E16384"));

        grid.mark(Dimension.of("B1:C3"));

        assert !grid.test(1, 1);
        assert grid.test(1, 2);
        assert grid.test(2, 2);
        assert grid.test(3, 3);
        assert !grid.test(4, 2);
        assert !grid.test(3, 4);
    }
}
