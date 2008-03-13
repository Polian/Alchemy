/*
 *  This file is part of the Alchemy project - http://al.chemy.org
 * 
 *  Copyright (c) 2007 Karl D.D. Willis
 * 
 *  Alchemy is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  Alchemy is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with Alchemy.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package org.alchemy.core;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;

/**
 * AlcSubSlider
 * 
 * 
 */
public class AlcSubSlider extends JPanel implements AlcConstants{

    public JSlider slider;

    public AlcSubSlider(String name, int minValue, int maxValue, int startValue) {

        // Top Left Bottom Right
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        slider = new JSlider(JSlider.HORIZONTAL, minValue, maxValue, startValue);
        //slider.setMajorTickSpacing(85); // sets numbers for biggest tick marks
        slider.setMinorTickSpacing(10);  // smaller tick marks
        //slider.setPaintTicks(true);     // display the ticks

        // This causes misalignment on a mac so for now readjust it
        int sliderHeight = 23;
        int sliderY = 3;
        if (Alchemy.PLATFORM == MACOSX) {
            sliderHeight = 20;
            sliderY = 4;
        }

        //alphaSlider.setUI(new BasicSliderUI(alphaSlider));
        slider.setOpaque(false);
        //alphaSlider.setBackground(Color.black);
        //alphaSlider.setForeground(Color.black);
        slider.setPreferredSize(new Dimension(75, sliderHeight));
        slider.setBorder(BorderFactory.createEmptyBorder(sliderY, 0, 0, 0));
        this.add(slider);

        JLabel label = new JLabel(name);
        label.setFont(AlcToolBar.subToolBarFont);
        label.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 2));
        //label.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        this.add(label);


    }
}