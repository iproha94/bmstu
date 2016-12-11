#!/usr/bin/env python
#coding: utf-8

import numpy as np

def generateData(n):
	"""
	generates a 2D linearly separable dataset with n samples.
	создает двухмерные линейно разделимые наборы данных с N выборками

	The thired element of the sample is the label
	третий элемент выборки - это метка
	"""
    xb = (np.random.rand(n) * 2 -1) / 2 - 0.5 #[-1, 0)
    yb = (np.random.rand(n) * 2 -1) / 2 + 0.5 #[0, 1)
    xr = (np.random.rand(n) * 2 -1) / 2 + 0.5 #[0, 1)
    yr = (np.random.rand(n) * 2 -1) / 2 - 0.5 #[-1, 0)

    inputs = []
    inputs.extend([[xb[i], yb[i], 1] for i in range(n)])
    inputs.extend([[xr[i], yr[i], -1] for i in range(n)])
    
    return inputs